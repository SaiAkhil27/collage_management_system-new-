package com.Collage.Management.CollageManagementSyatem.services;

import com.Collage.Management.CollageManagementSyatem.dtos.AdmissionDTO;
import com.Collage.Management.CollageManagementSyatem.entiites.AdmissionEntity;

import java.util.List;

public interface AdmissionServiceInterface {

    AdmissionDTO getAdmissionBydId(Long admissionId);

    List<AdmissionDTO> getAllAdmissions();

    AdmissionDTO createAdmission(AdmissionDTO admissionEntity);
}
