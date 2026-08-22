package com.Collage.Management.CollageManagementSyatem.services;

import com.Collage.Management.CollageManagementSyatem.dtos.StudentDTO;
import com.Collage.Management.CollageManagementSyatem.dtos.StudentWithSubjects;
import com.Collage.Management.CollageManagementSyatem.entiites.ProfessorEntity;
import com.Collage.Management.CollageManagementSyatem.entiites.Standard;
import com.Collage.Management.CollageManagementSyatem.entiites.StudentEntity;
import com.Collage.Management.CollageManagementSyatem.entiites.SubjectEntity;
import com.Collage.Management.CollageManagementSyatem.exceptions.ResourceNotFoundException;
import com.Collage.Management.CollageManagementSyatem.repositories.StandardRepository;
import com.Collage.Management.CollageManagementSyatem.repositories.StudentRepository;
import com.Collage.Management.CollageManagementSyatem.repositories.SubjectRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class StudentService implements StudentServiceInterface {

    private final StudentRepository studentRepository;
    private final SubjectRepository subjectRepository;

    private final StandardRepository standardRepository;

    public StudentService(StudentRepository studentRepository,
                          SubjectRepository subjectRepository, StandardRepository standardRepository) {
        this.studentRepository = studentRepository;
        this.subjectRepository = subjectRepository;
        this.standardRepository = standardRepository;
    }

    // ── helper ──────────────────────────────────────────
    private StudentDTO mapToDTO(StudentEntity student) {
        StudentDTO dto = new StudentDTO();
        dto.setId(student.getId());
        dto.setName(student.getName());
        dto.setEmail(student.getEmail());
        dto.setPhone(student.getPhone());
        dto.setAddress(student.getAddress());
        dto.setRole(student.getRole());

        if (student.getAdmission() != null) {
            dto.setAdmissionId(student.getAdmission().getId());
        }

        if (student.getStudentProfessor() != null) {
            dto.setProfessorIds(student.getStudentProfessor()
                    .stream()
                    .map(ProfessorEntity::getId)
                    .collect(Collectors.toSet()));
        }

        if (student.getSubjectsOfStudents() != null) {
            dto.setSubjectIds(student.getSubjectsOfStudents()
                    .stream()
                    .map(SubjectEntity::getId)
                    .collect(Collectors.toSet()));
        }

        if (student.getStandard() != null) {
            dto.setStandardId(student.getStandard().getId());
        }

        return dto;
    }

    // ── service methods ──────────────────────────────────
    @Override
    public StudentDTO createNewStudent(StudentDTO studentDTO) {
        StudentEntity student = new StudentEntity();
        student.setName(studentDTO.getName());
        student.setEmail(studentDTO.getEmail());
        student.setPhone(studentDTO.getPhone());
        student.setAddress(studentDTO.getAddress());
        student.setRole(studentDTO.getRole());
        StudentEntity saved = studentRepository.save(student);
        return mapToDTO(saved);
    }

    @Override
    public StudentDTO getStudentById(Long studentId) {
        StudentEntity student = studentRepository.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Student not found with id: " + studentId));
        return mapToDTO(student);
    }

    @Override
    public List<StudentDTO> getAllStudents() {
        return studentRepository.findAll()
                .stream()
                .map(this::mapToDTO)
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

        subject.getStudentSubjects().add(student);
        subjectRepository.save(subject);

        student.getSubjectsOfStudents().add(subject);
        StudentEntity saved = studentRepository.save(student);
        return mapToDTO(saved);
    }

    @Override
    public StudentWithSubjects getStudentWithSubject(Long studentId) {
        StudentEntity student = studentRepository.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Student not found with id: " + studentId));

        Set<String> studentSubjects = new HashSet<>();
        if (student.getSubjectsOfStudents() != null) {
            for (SubjectEntity subject : student.getSubjectsOfStudents()) {
                studentSubjects.add(subject.getName());
            }
        }

        String studentStandard = "N/A";
        if (student.getStandard() != null) {
            studentStandard = student.getStandard().getStandardNumber()
                    + " - Section "
                    + student.getStandard().getSection();
        }

        StudentWithSubjects result = new StudentWithSubjects();
        result.setStudentName(student.getName());
        result.setSubjects(studentSubjects);
        result.setStandard(studentStandard);
        return result;
    }
}