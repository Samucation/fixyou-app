package com.fcamara.builder;

import com.fcamara.dto.ProfileDTO;
import com.fcamara.model.Profile;
import com.fcamara.model.Unit;
import org.springframework.stereotype.Component;

@Component
public class ProfileMapper {

    public ProfileDTO toDTO(Profile profile) {
        if (profile == null) return null;

        return new ProfileDTO(
                profile.getId(),
                profile.getPreferredShift(),
                profile.getJobTitle(),
                profile.getUnit() != null ? profile.getUnit().getId() : null
        );
    }

    public Profile toEntity(ProfileDTO dto, Unit unit) {
        if (dto == null) return null;

        return Profile.Builder.aProfile()
                .id(dto.id())
                .preferredShift(dto.preferredShift())
                .jobTitle(dto.jobTitle())
                .unit(unit)
                .build();
    }
}
