package com.fcamara.model;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "profile_preferences", schema = "fixyou")
public class ProfilePreferences {

    @Id
    private UUID id;

    @Column(name = "profile_id", nullable = false)
    private UUID profileId;

    @Column(name = "preference_id", nullable = false)
    private UUID preferenceId;

    @Column(nullable = false)
    private String value;

    public ProfilePreferences() {}

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getProfileId() {
        return profileId;
    }

    public void setProfileId(UUID profileId) {
        this.profileId = profileId;
    }

    public UUID getPreferenceId() {
        return preferenceId;
    }

    public void setPreferenceId(UUID preferenceId) {
        this.preferenceId = preferenceId;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
