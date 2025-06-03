package com.fcamara.builder;

import com.fcamara.dto.RoleDTO;
import com.fcamara.model.Role;
import com.fcamara.model.UserRole;
import org.springframework.stereotype.Component;

@Component
public class RoleMapper {

    public RoleDTO toDTO(Role role) {
        if (role == null) return null;

        return new RoleDTO(
                role.getId(),
                role.getName() != null ? role.getName().name() : null
        );
    }

    public Role toEntity(RoleDTO dto) {
        if (dto == null) return null;

        return Role.Builder.aRole()
                .id(dto.id())
                .name(dto.name() != null ? UserRole.valueOf(dto.name()) : null)
                .build();
    }
}
