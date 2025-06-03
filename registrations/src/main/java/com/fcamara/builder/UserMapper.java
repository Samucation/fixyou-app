package com.fcamara.builder;

import com.fcamara.dto.UserDTO;
import com.fcamara.model.Role;
import com.fcamara.model.User;
import com.fcamara.model.Profile;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class UserMapper {

    public UserDTO toDTO(User user) {
        if (user == null) return null;

        Set<String> roleNames = user.getRoles() != null
                ? user.getRoles().stream()
                .map(role -> role.getName().name())
                .collect(Collectors.toSet())
                : Set.of();

        return new UserDTO(
                user.getId(),
                user.getUsername(),
                user.getKeycloakId(),
                user.getProfile() != null ? user.getProfile().getId() : null,
                roleNames
        );
    }

    public User toEntity(UserDTO dto, Profile profile, Set<Role> roles) {
        if (dto == null) return null;

        return User.Builder.anUser()
                .id(dto.id())
                .username(dto.username())
                .keycloakId(dto.keycloakId())
                .profile(profile)
                .roles(roles)
                .build();
    }
}
