package com.Collage.Management.CollageManagementSyatem.controllers;

import com.Collage.Management.CollageManagementSyatem.entiites.Exam;
import com.Collage.Management.CollageManagementSyatem.services.ExamServiceInterface;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping(value = "/exam")
public class ExamController {

    private ExamServiceInterface examServiceInterface;

    public ExamController(ExamServiceInterface examServiceInterface) {
        this.examServiceInterface = examServiceInterface;
    }

    @PostMapping("/postMarks")
    public Exam addExam(@RequestBody Exam exam){
       return examServiceInterface.addExam(exam);
   }

    @GetMapping("/{id}")
    public Exam findById(@PathVariable Long id){
        return examServiceInterface.getExamsById(id);
   }

    @GetMapping("/{examId}/student/{studentId}")
    public Map<String,Integer> getMarksByStudent(@PathVariable Long examId,@PathVariable Long studentId){
        return examServiceInterface.getMarksByStudent(examId,studentId);
   }



}
