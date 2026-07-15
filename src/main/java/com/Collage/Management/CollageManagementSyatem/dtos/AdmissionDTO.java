package com.Collage.Management.CollageManagementSyatem.dtos;

import com.Collage.Management.CollageManagementSyatem.entiites.StudentEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class AdmissionDTO {

    private Long id;
    private Integer fees;
    private StudentEntity admittedStudent;
}
