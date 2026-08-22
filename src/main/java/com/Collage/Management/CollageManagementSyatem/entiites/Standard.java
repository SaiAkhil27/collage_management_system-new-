package com.Collage.Management.CollageManagementSyatem.entiites;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "standards")
public class Standard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long standardNumber;    // 1 to 12

    @Column(nullable = false)
    private String section;            // A, B, C

    // One standard has many students
    @OneToMany(mappedBy = "standard")
    private Set<StudentEntity> students;
}