package com.Collage.Management.CollageManagementSyatem.services;

import com.Collage.Management.CollageManagementSyatem.dtos.StudentDTO;
import com.Collage.Management.CollageManagementSyatem.entiites.StudentEntity;
import com.Collage.Management.CollageManagementSyatem.entiites.SubjectEntity;
import com.Collage.Management.CollageManagementSyatem.exceptions.ResourceNotFoundException;
import com.Collage.Management.CollageManagementSyatem.repositories.StudentRepository;
import com.Collage.Management.CollageManagementSyatem.repositories.SubjectRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentService implements StudentServiceInterface {

    private final StudentRepository studentRepository;
    private final SubjectRepository subjectRepository;
    private final ModelMapper modelMapper;

    public StudentService(StudentRepository studentRepository,
                          SubjectRepository subjectRepository,
                          ModelMapper modelMapper) {
        this.studentRepository = studentRepository;
        this.subjectRepository = subjectRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public StudentDTO createNewStudent(StudentDTO studentDTO) {
        StudentEntity student = modelMapper.map(studentDTO, StudentEntity.class);
        StudentEntity saved = studentRepository.save(student);
        return modelMapper.map(saved, StudentDTO.class);
    }

    @Override
    public StudentDTO getStudentById(Long studentId) {
        StudentEntity student = studentRepository.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Student not found with id: " + studentId));
        return modelMapper.map(student, StudentDTO.class);
    }

    @Override
    public List<StudentDTO> getAllStudents() {
        return studentRepository.findAll()
                .stream()
                .map(student -> modelMapper.map(student, StudentDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public StudentDTO getSubjectsOfStudents(Long studentId, Long subjectId) {
        StudentEntity student = studentRepository.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Student not found with id: " + studentId));

        SubjectEntity subject = subjectRepository.findById(subjectId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Subject not found with id: " + subjectId));

        // Set both sides of the ManyToMany relationship
        subject.getStudentSubjects().add(student);
        subjectRepository.save(subject);

        student.getSubjectsOfStudents().add(subject);
        StudentEntity saved = studentRepository.save(student);

        return modelMapper.map(saved, StudentDTO.class);
    }
}