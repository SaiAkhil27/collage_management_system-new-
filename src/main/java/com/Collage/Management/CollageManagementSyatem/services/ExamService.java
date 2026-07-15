package com.Collage.Management.CollageManagementSyatem.services;

import com.Collage.Management.CollageManagementSyatem.entiites.Exam;
import com.Collage.Management.CollageManagementSyatem.exceptions.ResourceNotFoundException;
import com.Collage.Management.CollageManagementSyatem.repositories.ExamRepository;
import com.Collage.Management.CollageManagementSyatem.repositories.MarksRepository;
import com.Collage.Management.CollageManagementSyatem.repositories.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class ExamService implements ExamServiceInterface {

    private final ExamRepository examRepository;
    private final StudentRepository studentRepository;
    private final MarksRepository marksRepository;

    public ExamService(ExamRepository examRepository,
                       StudentRepository studentRepository,
                       MarksRepository marksRepository) {
        this.examRepository = examRepository;
        this.studentRepository = studentRepository;
        this.marksRepository = marksRepository;
    }

    @Override
    public Exam getExamsById(Long id) {
        return examRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Exam not found with id: " + id));
    }

    @Override
    public Exam addExam(Exam exam) {
        return examRepository.save(exam);
    }

    @Override
    public Map<String, Integer> getMarksByStudent(Long examId, Long studentId) {
        examRepository.findById(examId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Exam not found with id: " + examId));

        studentRepository.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Student not found with id: " + studentId));

        List<Object[]> results = marksRepository.findMarksByExamAndStudent(examId, studentId);

        Map<String, Integer> marksMap = new LinkedHashMap<>();
        for (Object[] row : results) {
            String subjectName = (String) row[0];
            Integer mark = (Integer) row[1];
            marksMap.put(subjectName, mark);
        }
        return marksMap;
    }
}