package com.fcamara.repository;

import com.fcamara.model.ProfilePreferences;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ProfilePreferencesRepository extends JpaRepository<ProfilePreferences, UUID> {
    List<ProfilePreferences> findAllByProfileId(UUID profileId);
}
