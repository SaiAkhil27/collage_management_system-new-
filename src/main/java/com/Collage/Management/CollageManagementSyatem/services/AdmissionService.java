package com.Collage.Management.CollageManagementSyatem.services;

import com.Collage.Management.CollageManagementSyatem.dtos.AdmissionDTO;
import com.Collage.Management.CollageManagementSyatem.entiites.AdmissionEntity;
import com.Collage.Management.CollageManagementSyatem.entiites.StudentEntity;
import com.Collage.Management.CollageManagementSyatem.exceptions.ResourceNotFoundException;
import com.Collage.Management.CollageManagementSyatem.repositories.AdmissionRepository;
import com.Collage.Management.CollageManagementSyatem.repositories.StudentRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdmissionService implements AdmissionServiceInterface {

    private final ModelMapper modelMapper;
    private final AdmissionRepository admissionRepository;
    private final StudentRepository studentRepository;

    public AdmissionService(ModelMapper modelMapper,
                            AdmissionRepository admissionRepository,
                            StudentRepository studentRepository) {
        this.modelMapper = modelMapper;
        this.admissionRepository = admissionRepository;
        this.studentRepository = studentRepository;
    }

    public AdmissionDTO mapAdmissionToStudent(Long admissionId, Long studentId) {
        AdmissionEntity admission = admissionRepository.findById(admissionId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Admission not found with id: " + admissionId));

        StudentEntity student = studentRepository.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Student not found with id: " + studentId));

        admission.setAdmittedStudent(student);
        AdmissionEntity updated = admissionRepository.save(admission);
        return modelMapper.map(updated, AdmissionDTO.class);
    }

    @Override
    public AdmissionDTO getAdmissionBydId(Long admissionId) {
        AdmissionEntity admission = admissionRepository.findById(admissionId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Admission not found with id: " + admissionId));
        return modelMapper.map(admission, AdmissionDTO.class);
    }

    @Override
    public List<AdmissionDTO> getAllAdmissions() {
        return admissionRepository.findAll()
                .stream()
                .map(admission -> modelMapper.map(admission, AdmissionDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public AdmissionDTO createAdmission(AdmissionDTO admissionDTO) {
        AdmissionEntity admission = modelMapper.map(admissionDTO, AdmissionEntity.class);
        AdmissionEntity saved = admissionRepository.save(admission);
        return modelMapper.map(saved, AdmissionDTO.class);
    }
}