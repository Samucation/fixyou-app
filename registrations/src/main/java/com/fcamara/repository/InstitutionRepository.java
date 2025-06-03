package com.fcamara.repository;

import com.fcamara.model.Institution;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InstitutionRepository extends JpaRepository<Institution, Long> {
}