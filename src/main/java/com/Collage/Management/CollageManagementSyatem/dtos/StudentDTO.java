package com.Collage.Management.CollageManagementSyatem.dtos;

import com.Collage.Management.CollageManagementSyatem.configs.Role;
import com.Collage.Management.CollageManagementSyatem.entiites.AdmissionEntity;
import com.Collage.Management.CollageManagementSyatem.entiites.ProfessorEntity;
import com.Collage.Management.CollageManagementSyatem.entiites.SubjectEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentDTO {
    private Long id;
    private String name;
    private String email;
    private String phone;
    private String address;
    private Role role;
    private AdmissionEntity admittedStud;
    private Set<ProfessorEntity> studentProfessor;
    private Set<SubjectEntity> subjectsOfStudents;

}
