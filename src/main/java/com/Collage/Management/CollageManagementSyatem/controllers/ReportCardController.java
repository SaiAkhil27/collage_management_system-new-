package com.Collage.Management.CollageManagementSyatem.controllers;

import com.Collage.Management.CollageManagementSyatem.services.ReportCardService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/report")
public class ReportCardController {

    private final ReportCardService reportCardService;

    public ReportCardController(ReportCardService reportCardService) {
        this.reportCardService = reportCardService;
    }

    // Opens PDF in browser
    @GetMapping("/{studentId}/exam/{examId}/view")
    public ResponseEntity<byte[]> viewReportCard(@PathVariable Long studentId,
                                                 @PathVariable Long examId) {
        byte[] pdf = reportCardService.generateReportCard(studentId, examId);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_PDF_VALUE)
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=report_card.pdf")
                .body(pdf);
    }

    // Downloads PDF as file
    @GetMapping("/{studentId}/exam/{examId}/download")
    public ResponseEntity<byte[]> downloadReportCard(@PathVariable Long studentId,
                                                     @PathVariable Long examId) {
        byte[] pdf = reportCardService.generateReportCard(studentId, examId);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_PDF_VALUE)
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=report_card_student_" + studentId + ".pdf")
                .body(pdf);
    }
}