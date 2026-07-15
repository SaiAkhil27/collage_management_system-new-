package com.Collage.Management.CollageManagementSyatem.repositories;

import com.Collage.Management.CollageManagementSyatem.entiites.SubjectEntity;
import org.hibernate.type.descriptor.converter.spi.JpaAttributeConverter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubjectRepository extends JpaRepository<SubjectEntity,Long> {
}
