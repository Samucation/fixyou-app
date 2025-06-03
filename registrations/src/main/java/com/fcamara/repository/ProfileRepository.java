package com.fcamara.repository;

import com.fcamara.model.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProfileRepository extends JpaRepository<Profile, Long> {
    Optional<Profile> findByJobTitle(String jobTitle);
}
