package com.Collage.Management.CollageManagementSyatem.services;

import com.Collage.Management.CollageManagementSyatem.dtos.ProfessorDTO;
import com.Collage.Management.CollageManagementSyatem.entiites.ProfessorEntity;
import com.Collage.Management.CollageManagementSyatem.entiites.StudentEntity;
import com.Collage.Management.CollageManagementSyatem.entiites.SubjectEntity;
import com.Collage.Management.CollageManagementSyatem.exceptions.ResourceNotFoundException;
import com.Collage.Management.CollageManagementSyatem.repositories.ProfessorRepository;
import com.Collage.Management.CollageManagementSyatem.repositories.SubjectRepository;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class StudentProfessor implements StudentProfessorServiceInterface {

    private final ProfessorRepository professorRepository;
    private final SubjectRepository subjectRepository;

    public StudentProfessor(ProfessorRepository professorRepository,
                            SubjectRepository subjectRepository) {
        this.professorRepository = professorRepository;
        this.subjectRepository = subjectRepository;
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

    // ── service method ──────────────────────────────────
    @Override
    public ProfessorDTO getprofessorSubjects(Long professorId, Long subjectId) {
        ProfessorEntity professor = professorRepository.findById(professorId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Professor not found with id: " + professorId));

        boolean hasSubject = professor.getSubjects()
                .stream()
                .anyMatch(subject -> subject.getId().equals(subjectId));

        if (!hasSubject) {
            throw new ResourceNotFoundException(
                    "Subject with id: " + subjectId +
                            " not assigned to professor with id: " + professorId);
        }

        ProfessorDTO dto = mapToDTO(professor);
        professor.getSubjects()
                .stream()
                .filter(s -> s.getId().equals(subjectId))
                .findFirst()
                .ifPresent(s -> dto.setTeachedSubs(s.getName()));

        return dto;
    }
}