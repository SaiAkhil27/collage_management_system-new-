package com.Collage.Management.CollageManagementSyatem.repositories;

import com.Collage.Management.CollageManagementSyatem.entiites.ProfessorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfessorRepository extends JpaRepository<ProfessorEntity,Long> {
}
