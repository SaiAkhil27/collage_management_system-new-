package com.Collage.Management.CollageManagementSyatem.dtos;

import com.Collage.Management.CollageManagementSyatem.configs.Role;
import com.Collage.Management.CollageManagementSyatem.entiites.StudentEntity;
import com.Collage.Management.CollageManagementSyatem.entiites.SubjectEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProfessorDTO {

    private Long id;
    private String name;
    private String email;
    private String phone;
    private String address;
    private Role role;
    private String teachedSubs;
    private Set<SubjectEntity> subjects;
    private Set<StudentEntity> students;
}
