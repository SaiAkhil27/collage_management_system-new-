package com.Collage.Management.CollageManagementSyatem.repositories;

import com.Collage.Management.CollageManagementSyatem.entiites.Marks;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MarksRepository extends JpaRepository<Marks, Long> {

    @Query("SELECT m.subject.name, m.marks FROM Marks m " +
            "WHERE m.exam.id = :examId AND m.student.id = :studentId")
    List<Object[]> findMarksByExamAndStudent(
            @Param("examId") Long examId,
            @Param("studentId") Long studentId
    );
}