package com.fcamara.builder;

import com.fcamara.dto.ProfilePreferencesDTO;
import com.fcamara.model.ProfilePreferences;
import org.springframework.stereotype.Component;

@Component
public class ProfilePreferencesMapper {

    public ProfilePreferencesDTO toDTO(ProfilePreferences entity) {
        if (entity == null) return null;

        return new ProfilePreferencesDTO(
                entity.getId(),
                entity.getProfileId(),
                entity.getPreferenceId(),
                entity.getValue()
        );
    }

    public ProfilePreferences toEntity(ProfilePreferencesDTO dto) {
        if (dto == null) return null;

        ProfilePreferences entity = new ProfilePreferences();
        entity.setId(dto.id());
        entity.setProfileId(dto.profileId());
        entity.setPreferenceId(dto.preferenceId());
        entity.setValue(dto.value());
        return entity;
    }
}
