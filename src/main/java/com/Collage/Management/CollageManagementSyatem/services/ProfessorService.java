package com.Collage.Management.CollageManagementSyatem.services;

import com.Collage.Management.CollageManagementSyatem.dtos.ProfessorDTO;
import com.Collage.Management.CollageManagementSyatem.dtos.SubjectsProfesorThought;
import com.Collage.Management.CollageManagementSyatem.entiites.ProfessorEntity;
import com.Collage.Management.CollageManagementSyatem.entiites.StudentEntity;
import com.Collage.Management.CollageManagementSyatem.entiites.SubjectEntity;
import com.Collage.Management.CollageManagementSyatem.exceptions.ResourceNotFoundException;
import com.Collage.Management.CollageManagementSyatem.repositories.ProfessorRepository;
import com.Collage.Management.CollageManagementSyatem.repositories.StudentRepository;
import com.Collage.Management.CollageManagementSyatem.repositories.SubjectRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ProfessorService implements ProfessorServiceInterface {

    private final ProfessorRepository professorRepository;
    private final SubjectRepository subjectRepository;
    private final StudentRepository studentRepository;
    private final ModelMapper modelMapper;

    public ProfessorService(ProfessorRepository professorRepository,
                            SubjectRepository subjectRepository,
                            StudentRepository studentRepository,
                            ModelMapper modelMapper) {
        this.professorRepository = professorRepository;
        this.subjectRepository = subjectRepository;
        this.studentRepository = studentRepository;
        this.modelMapper = modelMapper;
    }

    // ── helper ──────────────────────────────────────────
    private ProfessorDTO mapToDTO(ProfessorEntity professor) {
        ProfessorDTO dto = new ProfessorDTO();
        dto.setId(professor.getId());
        dto.setName(professor.getName());
        dto.setEmail(professor.getEmail());
        dto.setPhone(professor.getPhone());
        dto.setAddress(professor.getAddress());
        dto.setRole(professor.getRole());

        if (professor.getSubjects() != null) {
            dto.setSubjectsIds(professor.getSubjects()
                    .stream()
                    .map(SubjectEntity::getId)
                    .collect(Collectors.toSet()));
        }

        if (professor.getStudents() != null) {
            dto.setStudentsIds(professor.getStudents()
                    .stream()
                    .map(StudentEntity::getId)
                    .collect(Collectors.toSet()));
        }

        return dto;
    }

    // ── service methods ──────────────────────────────────
    @Override
    public ProfessorDTO getProfessorById(Long professorId) {
        ProfessorEntity professor = professorRepository.findById(professorId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Professor not found with id: " + professorId));
        return mapToDTO(professor);
    }

    @Override
    public List<ProfessorDTO> getAllProfessors() {
        return professorRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ProfessorDTO createProfessor(ProfessorDTO professorDTO) {
        ProfessorEntity professor = modelMapper.map(professorDTO, ProfessorEntity.class);
        ProfessorEntity saved = professorRepository.save(professor);
        return mapToDTO(saved);
    }

    @Override
    public ProfessorDTO assignProfessorToSubjects(Long professorId, Long subjectId) {
        ProfessorEntity professor = professorRepository.findById(professorId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Professor not found with id: " + professorId));

        SubjectEntity subject = subjectRepository.findById(subjectId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Subject not found with id: " + subjectId));

        subject.setProfessor(professor);
        subjectRepository.save(subject);

        professor.getSubjects().add(subject);
        ProfessorEntity saved = professorRepository.save(professor);

        ProfessorDTO dto = mapToDTO(saved);
        dto.setTeachedSubs(subject.getName());
        return dto;
    }

    @Override
    public SubjectsProfesorThought subjectsProfTought(Long profId) {
        ProfessorEntity professor = professorRepository.findById(profId).orElseThrow(()-> new ResourceNotFoundException(
                "Professor not found with id: " + profId));
        SubjectsProfesorThought subjectsProfesorThought1 = new SubjectsProfesorThought();
        subjectsProfesorThought1.setProfName(professor.getName());
       Set<SubjectEntity> subject = professor.getSubjects();
       List<String> subjectForProf = new ArrayList<>();

        for (SubjectEntity subject1 : subject) {
            subjectForProf.add(subject1.getName());
        }
        subjectsProfesorThought1.setSubjectName(subjectForProf);

        return subjectsProfesorThought1;


    }

    @Override
    public ProfessorDTO assignStudentsToProfessor(Long professorId, Long studentId) {
        ProfessorEntity professor = professorRepository.findById(professorId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Professor not found with id: " + professorId));

        StudentEntity student = studentRepository.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Student not found with id: " + studentId));

        student.getStudentProfessor().add(professor);
        studentRepository.save(student);

        professor.getStudents().add(student);
        ProfessorEntity saved = professorRepository.save(professor);

        return mapToDTO(saved);
    }
}