package com.Collage.Management.CollageManagementSyatem.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MarksDTO {
    private Long id;
    private Long student_id;
    private Long subject_id;
    private Integer  marks;   // kept as Long to match DTO contract; MarksService converts to Integer when saving
    private Long exam_id;
}