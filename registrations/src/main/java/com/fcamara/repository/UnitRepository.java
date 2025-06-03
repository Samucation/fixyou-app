package com.fcamara.repository;

import com.fcamara.model.Unit;
import com.fcamara.model.Institution;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UnitRepository extends JpaRepository<Unit, Long> {

    List<Unit> findByInstitution(Institution institution);
    Optional<Unit> findByCode(String code);


}