package com.Collage.Management.CollageManagementSyatem.services;

import com.Collage.Management.CollageManagementSyatem.dtos.SubjectDTO;
import com.Collage.Management.CollageManagementSyatem.entiites.SubjectEntity;
import com.Collage.Management.CollageManagementSyatem.exceptions.ResourceNotFoundException;
import com.Collage.Management.CollageManagementSyatem.repositories.SubjectRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SubjectService implements SubjectServiceInterface {

    private final SubjectRepository subjectRepository;
    private final ModelMapper modelMapper;

    public SubjectService(SubjectRepository subjectRepository, ModelMapper modelMapper) {
        this.subjectRepository = subjectRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public SubjectDTO getSubjectById(Long subjectId) {
        SubjectEntity subject = subjectRepository.findById(subjectId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Subject not found with id: " + subjectId));
        return modelMapper.map(subject, SubjectDTO.class);
    }

    @Override
    public SubjectDTO createSubject(SubjectDTO subjectDTO) {
        SubjectEntity subject = modelMapper.map(subjectDTO, SubjectEntity.class);
        SubjectEntity saved = subjectRepository.save(subject);
        return modelMapper.map(saved, SubjectDTO.class);
    }

    @Override
    public List<SubjectDTO> getAllSubjects() {
        return subjectRepository.findAll()
                .stream()
                .map(subject -> modelMapper.map(subject, SubjectDTO.class))
                .collect(Collectors.toList());
    }
}