package com.Collage.Management.CollageManagementSyatem.services;

import com.Collage.Management.CollageManagementSyatem.dtos.SubjectDTO;
import com.Collage.Management.CollageManagementSyatem.entiites.StudentEntity;
import com.Collage.Management.CollageManagementSyatem.entiites.SubjectEntity;
import com.Collage.Management.CollageManagementSyatem.exceptions.ResourceNotFoundException;
import com.Collage.Management.CollageManagementSyatem.repositories.SubjectRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SubjectService implements SubjectServiceInterface {

    private final SubjectRepository subjectRepository;

    public SubjectService(SubjectRepository subjectRepository) {
        this.subjectRepository = subjectRepository;
    }

    // ── helper ──────────────────────────────────────────
    private SubjectDTO mapToDTO(SubjectEntity subject) {
        SubjectDTO dto = new SubjectDTO();
        dto.setId(subject.getId());
        dto.setName(subject.getName());
        dto.setSubjectType(subject.getSubjectType());

        if (subject.getProfessor() != null) {
            dto.setProfessorId(subject.getProfessor().getId());
        }

        if (subject.getStudentSubjects() != null) {
            dto.setStudentIds(subject.getStudentSubjects()
                    .stream()
                    .map(StudentEntity::getId)
                    .collect(Collectors.toSet()));
        }

        return dto;
    }

    // ── service methods ──────────────────────────────────
    @Override
    public SubjectDTO getSubjectById(Long subjectId) {
        SubjectEntity subject = subjectRepository.findById(subjectId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Subject not found with id: " + subjectId));
        return mapToDTO(subject);
    }

    @Override
    public SubjectDTO createSubject(SubjectDTO subjectDTO) {
        SubjectEntity subject = new SubjectEntity();
        subject.setName(subjectDTO.getName());
        subject.setSubjectType(subjectDTO.getSubjectType());
        SubjectEntity saved = subjectRepository.save(subject);
        return mapToDTO(saved);
    }

    @Override
    public List<SubjectDTO> getAllSubjects() {
        return subjectRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }
}