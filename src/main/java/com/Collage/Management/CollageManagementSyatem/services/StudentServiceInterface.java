package com.Collage.Management.CollageManagementSyatem.services;

import com.Collage.Management.CollageManagementSyatem.dtos.StudentDTO;
import com.Collage.Management.CollageManagementSyatem.entiites.StudentEntity;

import java.util.List;

public interface StudentServiceInterface {
    StudentDTO getStudentById(Long studentId);

    List<StudentDTO> getAllStudents();

    StudentDTO createNewStudent(StudentDTO studentEntity);

    StudentDTO getSubjectsOfStudents(Long studentId, Long subjectId);
}
