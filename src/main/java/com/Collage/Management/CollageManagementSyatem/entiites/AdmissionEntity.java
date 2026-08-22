package com.Collage.Management.CollageManagementSyatem.entiites;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Admissions")
@EntityListeners(AuditingEntityListener.class)
public class AdmissionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private Integer fees;

    @OneToOne
    @JoinColumn(
            name = "admission_student_id",
            nullable = false,
            unique = true
    )
    private StudentEntity admittedStudent;


    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private  LocalDateTime lastModifiedDate;

    @CreatedBy
    private String personCreated;

    @LastModifiedBy
    private String personModified;
}