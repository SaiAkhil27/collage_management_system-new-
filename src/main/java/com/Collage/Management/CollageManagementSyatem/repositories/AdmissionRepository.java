package com.Collage.Management.CollageManagementSyatem.repositories;

import com.Collage.Management.CollageManagementSyatem.entiites.AdmissionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdmissionRepository extends JpaRepository<AdmissionEntity, Long> {

}
