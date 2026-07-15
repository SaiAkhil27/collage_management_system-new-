package com.Collage.Management.CollageManagementSyatem.services;

import com.Collage.Management.CollageManagementSyatem.dtos.ProfessorDTO;
import com.Collage.Management.CollageManagementSyatem.entiites.ProfessorEntity;

import java.util.List;

public interface ProfessorServiceInterface {
    ProfessorDTO getProfessorById(Long professorId);

    ProfessorDTO createProfessor(ProfessorDTO professorEntity);

    List<ProfessorDTO> getAllProfessors();

    ProfessorDTO assignStudentsToProfessor(Long professorId, Long studentId);

    ProfessorDTO assignProfessorToSubjects(Long professorId, Long subjectsId);

}
