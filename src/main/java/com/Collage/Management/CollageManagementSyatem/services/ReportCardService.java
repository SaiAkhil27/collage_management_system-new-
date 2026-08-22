package com.Collage.Management.CollageManagementSyatem.services;

import com.Collage.Management.CollageManagementSyatem.entiites.Marks;
import com.Collage.Management.CollageManagementSyatem.entiites.StudentEntity;
import com.Collage.Management.CollageManagementSyatem.entiites.Exam;
import com.Collage.Management.CollageManagementSyatem.exceptions.ResourceNotFoundException;
import com.Collage.Management.CollageManagementSyatem.repositories.ExamRepository;
import com.Collage.Management.CollageManagementSyatem.repositories.MarksRepository;
import com.Collage.Management.CollageManagementSyatem.repositories.StudentRepository;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.List;

@Service
public class ReportCardService {

    private final StudentRepository studentRepository;
    private final ExamRepository examRepository;
    private final MarksRepository marksRepository;

    public ReportCardService(StudentRepository studentRepository,
                             ExamRepository examRepository,
                             MarksRepository marksRepository) {
        this.studentRepository = studentRepository;
        this.examRepository = examRepository;
        this.marksRepository = marksRepository;
    }

    public byte[] generateReportCard(Long studentId, Long examId) {
        // Fetch student and exam
        StudentEntity student = studentRepository.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Student not found with id: " + studentId));

        Exam exam = examRepository.findById(examId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Exam not found with id: " + examId));

        // Fetch marks
        List<Object[]> results = marksRepository
                .findMarksByExamAndStudent(examId, studentId);

        if (results.isEmpty()) {
            throw new ResourceNotFoundException(
                    "No marks found for student: " + studentId + " in exam: " + examId);
        }

        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            Document document = new Document(PageSize.A4);
            PdfWriter.getInstance(document, out);
            document.open();

            // ── Fonts ──
            Font titleFont   = new Font(Font.FontFamily.HELVETICA, 20, Font.BOLD, BaseColor.WHITE);
            Font headerFont  = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD, BaseColor.WHITE);
            Font labelFont   = new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD, BaseColor.DARK_GRAY);
            Font valueFont   = new Font(Font.FontFamily.HELVETICA, 11, Font.NORMAL, BaseColor.DARK_GRAY);
            Font tableHeader = new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD, BaseColor.WHITE);
            Font tableData   = new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL, BaseColor.DARK_GRAY);
            Font totalFont   = new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD, BaseColor.DARK_GRAY);

            BaseColor darkBlue  = new BaseColor(31, 56, 100);
            BaseColor lightBlue = new BaseColor(214, 228, 240);
            BaseColor green     = new BaseColor(39, 174, 96);

            // ── Title ──
            PdfPTable titleTable = new PdfPTable(1);
            titleTable.setWidthPercentage(100);
            PdfPCell titleCell = new PdfPCell(new Phrase("SCHOOL MANAGEMENT SYSTEM", titleFont));
            titleCell.setBackgroundColor(darkBlue);
            titleCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            titleCell.setPadding(15);
            titleCell.setBorder(Rectangle.NO_BORDER);
            titleTable.addCell(titleCell);

            PdfPCell subTitleCell = new PdfPCell(
                    new Phrase("REPORT CARD", new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD, BaseColor.WHITE)));
            subTitleCell.setBackgroundColor(darkBlue);
            subTitleCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            subTitleCell.setPadding(5);
            subTitleCell.setBorder(Rectangle.NO_BORDER);
            titleTable.addCell(subTitleCell);
            document.add(titleTable);

            document.add(Chunk.NEWLINE);

            // ── Student Info ──
            PdfPTable infoTable = new PdfPTable(2);
            infoTable.setWidthPercentage(100);
            infoTable.setWidths(new float[]{1, 2});

            addInfoRow(infoTable, "Student Name", student.getName(), labelFont, valueFont, lightBlue);
            addInfoRow(infoTable, "Email", student.getEmail(), labelFont, valueFont, BaseColor.WHITE);

            String standard = student.getStandard() != null
                    ? student.getStandard().getStandardNumber() + " - " + student.getStandard().getSection()
                    : "N/A";
            addInfoRow(infoTable, "Standard", standard, labelFont, valueFont, lightBlue);
            addInfoRow(infoTable, "Exam", exam.getName(), labelFont, valueFont, BaseColor.WHITE);
            addInfoRow(infoTable, "Semester", exam.getSemister(), labelFont, valueFont, lightBlue);
            document.add(infoTable);

            document.add(Chunk.NEWLINE);

            // ── Marks Table ──
            PdfPTable marksTable = new PdfPTable(3);
            marksTable.setWidthPercentage(100);
            marksTable.setWidths(new float[]{3, 1, 1});

            // Table headers
            addTableHeader(marksTable, "Subject",  tableHeader, darkBlue);
            addTableHeader(marksTable, "Marks",    tableHeader, darkBlue);
            addTableHeader(marksTable, "Grade",    tableHeader, darkBlue);

            int total = 0;
            int count = 0;

            for (Object[] row : results) {
                String subjectName = (String) row[0];
                int mark = ((Number) row[1]).intValue();
                total += mark;
                count++;

                BaseColor rowColor = (count % 2 == 0) ? lightBlue : BaseColor.WHITE;
                addTableRow(marksTable, subjectName, String.valueOf(mark),
                        getGrade(mark), tableData, rowColor);
            }

            document.add(marksTable);

            document.add(Chunk.NEWLINE);

            // ── Summary ──
            int maxMarks = count * 100;
            double percentage = (total * 100.0) / maxMarks;
            String result = percentage >= 35 ? "PASS" : "FAIL";
            BaseColor resultColor = percentage >= 35 ? green : BaseColor.RED;

            PdfPTable summaryTable = new PdfPTable(3);
            summaryTable.setWidthPercentage(100);

            PdfPCell totalCell = new PdfPCell(
                    new Phrase("Total: " + total + " / " + maxMarks, totalFont));
            totalCell.setPadding(8);
            totalCell.setBackgroundColor(lightBlue);
            summaryTable.addCell(totalCell);

            PdfPCell percentCell = new PdfPCell(
                    new Phrase(String.format("Percentage: %.1f%%", percentage), totalFont));
            percentCell.setPadding(8);
            percentCell.setBackgroundColor(lightBlue);
            summaryTable.addCell(percentCell);

            PdfPCell resultCell = new PdfPCell(
                    new Phrase("Result: " + result,
                            new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD, resultColor)));
            resultCell.setPadding(8);
            resultCell.setBackgroundColor(lightBlue);
            summaryTable.addCell(resultCell);

            document.add(summaryTable);

            document.close();
            return out.toByteArray();

        } catch (Exception e) {
            throw new RuntimeException("Failed to generate report card: " + e.getMessage());
        }
    }

    // ── Helper methods ──────────────────────────────────

    private void addInfoRow(PdfPTable table, String label, String value,
                            Font labelFont, Font valueFont, BaseColor bg) {
        PdfPCell labelCell = new PdfPCell(new Phrase(label, labelFont));
        labelCell.setBackgroundColor(bg);
        labelCell.setPadding(8);
        table.addCell(labelCell);

        PdfPCell valueCell = new PdfPCell(new Phrase(value, valueFont));
        valueCell.setBackgroundColor(bg);
        valueCell.setPadding(8);
        table.addCell(valueCell);
    }

    private void addTableHeader(PdfPTable table, String text, Font font, BaseColor bg) {
        PdfPCell cell = new PdfPCell(new Phrase(text, font));
        cell.setBackgroundColor(bg);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setPadding(8);
        table.addCell(cell);
    }

    private void addTableRow(PdfPTable table, String subject, String marks,
                             String grade, Font font, BaseColor bg) {
        PdfPCell c1 = new PdfPCell(new Phrase(subject, font));
        c1.setBackgroundColor(bg); c1.setPadding(7);
        table.addCell(c1);

        PdfPCell c2 = new PdfPCell(new Phrase(marks, font));
        c2.setBackgroundColor(bg);
        c2.setHorizontalAlignment(Element.ALIGN_CENTER);
        c2.setPadding(7);
        table.addCell(c2);

        PdfPCell c3 = new PdfPCell(new Phrase(grade, font));
        c3.setBackgroundColor(bg);
        c3.setHorizontalAlignment(Element.ALIGN_CENTER);
        c3.setPadding(7);
        table.addCell(c3);
    }

    private String getGrade(int marks) {
        if (marks >= 90) return "A+";
        if (marks >= 80) return "A";
        if (marks >= 70) return "B+";
        if (marks >= 60) return "B";
        if (marks >= 50) return "C";
        if (marks >= 35) return "D";
        return "F";
    }
}
