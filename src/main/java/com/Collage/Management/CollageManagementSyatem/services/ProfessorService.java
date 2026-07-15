package com.Collage.Management.CollageManagementSyatem.services;

import com.Collage.Management.CollageManagementSyatem.dtos.ProfessorDTO;
import com.Collage.Management.CollageManagementSyatem.entiites.ProfessorEntity;
import com.Collage.Management.CollageManagementSyatem.entiites.StudentEntity;
import com.Collage.Management.CollageManagementSyatem.entiites.SubjectEntity;
import com.Collage.Management.CollageManagementSyatem.exceptions.ResourceNotFoundException;
import com.Collage.Management.CollageManagementSyatem.repositories.ProfessorRepository;
import com.Collage.Management.CollageManagementSyatem.repositories.StudentRepository;
import com.Collage.Management.CollageManagementSyatem.repositories.SubjectRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
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

    @Override
    public ProfessorDTO getProfessorById(Long professorId) {
        ProfessorEntity professor = professorRepository.findById(professorId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Professor not found with id: " + professorId));
        return modelMapper.map(professor, ProfessorDTO.class);
    }

    @Override
    public ProfessorDTO createProfessor(ProfessorDTO professorDTO) {
        ProfessorEntity professor = modelMapper.map(professorDTO, ProfessorEntity.class);
        ProfessorEntity saved = professorRepository.save(professor);
        return modelMapper.map(saved, ProfessorDTO.class);
    }

    @Override
    public List<ProfessorDTO> getAllProfessors() {
        return professorRepository.findAll()
                .stream()
                .map(professor -> modelMapper.map(professor, ProfessorDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public ProfessorDTO assignProfessorToSubjects(Long professorId, Long subjectId) {
        ProfessorEntity professor = professorRepository.findById(professorId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Professor not found with id: " + professorId));

        SubjectEntity subject = subjectRepository.findById(subjectId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Subject not found with id: " + subjectId));

        // Set both sides of the relationship
        subject.setProfessor(professor);
        subjectRepository.save(subject);

        // Add to professor's subject set (without overwriting existing ones)
        professor.getSubjects().add(subject);
        ProfessorEntity saved = professorRepository.save(professor);

        ProfessorDTO professorDTO = modelMapper.map(saved, ProfessorDTO.class);
        professorDTO.setTeachedSubs(subject.getName());
        return professorDTO;
    }

    @Override
    public ProfessorDTO assignStudentsToProfessor(Long professorId, Long studentId) {
        ProfessorEntity professor = professorRepository.findById(professorId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Professor not found with id: " + professorId));

        StudentEntity student = studentRepository.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Student not found with id: " + studentId));

        // Set both sides of the ManyToMany relationship
        student.getStudentProfessor().add(professor);
        studentRepository.save(student);

        professor.getStudents().add(student);
        ProfessorEntity saved = professorRepository.save(professor);

        return modelMapper.map(saved, ProfessorDTO.class);
    }
}