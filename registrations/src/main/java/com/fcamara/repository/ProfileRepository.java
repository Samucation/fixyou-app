package com.fcamara.repository;

import com.fcamara.model.Branch;
import com.fcamara.model.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Long> {
    List<Profile> findByBranch(Branch branch);
}
