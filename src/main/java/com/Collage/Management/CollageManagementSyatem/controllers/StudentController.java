package com.Collage.Management.CollageManagementSyatem.controllers;

import com.Collage.Management.CollageManagementSyatem.dtos.StudentDTO;
import com.Collage.Management.CollageManagementSyatem.dtos.StudentWithSubjects;
import com.Collage.Management.CollageManagementSyatem.entiites.StudentEntity;
import com.Collage.Management.CollageManagementSyatem.services.StudentService;
import com.Collage.Management.CollageManagementSyatem.services.StudentServiceInterface;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/Student")
public class StudentController {

    private final StudentServiceInterface studentServiceInterface;
    private final StudentService studentService;

    public StudentController(StudentServiceInterface studentServiceInterface, StudentService studentService) {
        this.studentServiceInterface = studentServiceInterface;
        this.studentService = studentService;
    }

    @GetMapping("/{studentId}")
    private StudentDTO getStudentById(@PathVariable Long studentId){
        return studentServiceInterface.getStudentById(studentId);
    }
    @GetMapping
    public List<StudentDTO> getAllStudents() {
        return studentServiceInterface.getAllStudents();
    }
    @PostMapping
    private StudentDTO createNewStudent(@RequestBody StudentDTO studentEntity){
        return studentServiceInterface.createNewStudent(studentEntity);
    }

    @PutMapping("/{studentId}/assignSubjectsToStudent/{subjectId}")
    private StudentDTO getSubjectsOfStudents(@PathVariable Long studentId,
                                                @PathVariable Long subjectId){
        return studentServiceInterface.getSubjectsOfStudents(studentId,subjectId);
    }
    @GetMapping("/studentWithSubject/{studentId}")
    private StudentWithSubjects studentWithSubjects(@PathVariable Long studentId){
        return studentServiceInterface.getStudentWithSubject(studentId);
    }
}
