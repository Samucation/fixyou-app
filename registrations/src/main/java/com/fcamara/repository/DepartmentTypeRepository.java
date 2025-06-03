package com.fcamara.repository;

import com.fcamara.model.DepartmentType;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DepartmentTypeRepository extends JpaRepository<DepartmentType, Long> {

    // Busca tipos globais ou específicos da instituição
    @Query("SELECT d FROM DepartmentType d WHERE d.institution IS NULL OR d.institution.id = :institutionId")
    List<DepartmentType> findAllByInstitutionOrGlobal(@Param("institutionId") Long institutionId);

}