package com.Collage.Management.CollageManagementSyatem.services;


import com.Collage.Management.CollageManagementSyatem.dtos.ProfessorDTO;

public interface StudentProfessorServiceInterface {
    ProfessorDTO getprofessorSubjects(Long professorId, Long subjectId);

}
