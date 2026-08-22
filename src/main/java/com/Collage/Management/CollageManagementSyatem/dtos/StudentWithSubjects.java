package com.Collage.Management.CollageManagementSyatem.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentWithSubjects {
   String studentName;
   Set<String> subjects;
   String standard;
}
