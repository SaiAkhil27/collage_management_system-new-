package com.Collage.Management.CollageManagementSyatem.services;

import com.Collage.Management.CollageManagementSyatem.dtos.SubjectDTO;

import java.util.List;

public interface SubjectServiceInterface {
    SubjectDTO getSubjectById(Long subjectId);

    SubjectDTO createSubject(SubjectDTO subjectEntity);
     List<SubjectDTO> getAllSubjects();
}
