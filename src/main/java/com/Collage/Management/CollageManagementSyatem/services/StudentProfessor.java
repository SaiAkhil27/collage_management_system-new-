package com.Collage.Management.CollageManagementSyatem.services;

import com.Collage.Management.CollageManagementSyatem.dtos.ProfessorDTO;
import com.Collage.Management.CollageManagementSyatem.entiites.ProfessorEntity;
import com.Collage.Management.CollageManagementSyatem.exceptions.ResourceNotFoundException;
import com.Collage.Management.CollageManagementSyatem.repositories.ProfessorRepository;
import com.Collage.Management.CollageManagementSyatem.repositories.SubjectRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class StudentProfessor implements StudentProfessorServiceInterface {

    private final ProfessorRepository professorRepository;
    private final SubjectRepository subjectRepository;
    private final ModelMapper modelMapper;

    public StudentProfessor(ProfessorRepository professorRepository,
                            SubjectRepository subjectRepository,
                            ModelMapper modelMapper) {
        this.professorRepository = professorRepository;
        this.subjectRepository = subjectRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public ProfessorDTO getprofessorSubjects(Long professorId, Long subjectId) {
        ProfessorEntity professor = professorRepository.findById(professorId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Professor not found with id: " + professorId));

        // Validate that this subject belongs to this professor
        boolean hasSubject = professor.getSubjects()
                .stream()
                .anyMatch(subject -> subject.getId().equals(subjectId));

        if (!hasSubject) {
            throw new ResourceNotFoundException(
                    "Subject with id: " + subjectId + " not assigned to professor with id: " + professorId);
        }

        ProfessorDTO dto = modelMapper.map(professor, ProfessorDTO.class);
        professor.getSubjects()
                .stream()
                .filter(s -> s.getId().equals(subjectId))
                .findFirst()
                .ifPresent(s -> dto.setTeachedSubs(s.getName()));
        return dto;
    }
}