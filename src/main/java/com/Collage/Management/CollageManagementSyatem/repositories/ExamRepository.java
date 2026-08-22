package com.Collage.Management.CollageManagementSyatem.repositories;

import com.Collage.Management.CollageManagementSyatem.entiites.Exam;
import org.apache.ibatis.annotations.Param;
import org.hibernate.sql.ast.tree.expression.JdbcParameter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExamRepository extends JpaRepository<Exam,Long> {

}
