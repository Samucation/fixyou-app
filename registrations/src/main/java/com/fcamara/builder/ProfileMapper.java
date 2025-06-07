package com.fcamara.builder;

import com.fcamara.dto.ProfileDTO;
import com.fcamara.model.Profile;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class ProfileMapper {

    public ProfileDTO toDTO(Profile profile) {
        if (profile == null) return null;

        return new ProfileDTO(
                profile.getId(),
                profile.getPerson(),
                profile.getPosition(),
                profile.getContract(),
                profile.getStartDate(),
                profile.isTermLgpd(),
                profile.isHasAdmin(),
                profile.isHasMobile(),
                profile.isHasManager(),
                profile.isActive()
        );
    }

    public Profile toEntity(ProfileDTO dto) {
        if (dto == null) return null;

        Profile profile = new Profile();
        profile.setId(dto.id() != null ? dto.id() : UUID.randomUUID());
        profile.setPerson(dto.person());
        profile.setPosition(dto.position());
        profile.setContract(dto.contract());
        profile.setStartDate(dto.startDate());
        profile.setTermLgpd(dto.termLgpd());
        profile.setHasAdmin(dto.hasAdmin());
        profile.setHasMobile(dto.hasMobile());
        profile.setHasManager(dto.hasManager());
        profile.setActive(dto.active());

        return profile;
    }
}
