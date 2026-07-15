package com.Collage.Management.CollageManagementSyatem.dtos;

import com.Collage.Management.CollageManagementSyatem.configs.SubjectType;
import com.Collage.Management.CollageManagementSyatem.entiites.ProfessorEntity;
import com.Collage.Management.CollageManagementSyatem.entiites.StudentEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubjectDTO {
    private Long id;
    private String name;
    private SubjectType subjectType;
    private ProfessorEntity professor;
    private Set<StudentEntity> studentSubjects;
}
