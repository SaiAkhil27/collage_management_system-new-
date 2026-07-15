package com.Collage.Management.CollageManagementSyatem.services;

import com.Collage.Management.CollageManagementSyatem.entiites.Exam;

import java.util.Map;

public interface ExamServiceInterface {

     Exam getExamsById(Long id);
     Exam addExam(Exam exam);

     Map<String, Integer> getMarksByStudent(Long examId, Long studentId);

}
