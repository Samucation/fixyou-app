package com.fcamara.repository;

import com.fcamara.model.Branch;
import com.fcamara.model.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BranchRepository extends JpaRepository<Branch, Long> {
    Optional<Branch> findByCode(String code);
    List<Branch> findByCompany(Company company);
    boolean existsByCode(String code);
    boolean existsByName(String name);
}