package com.fcamara.builder;

import com.fcamara.dto.UserContextRoleDTO;
import com.fcamara.model.*;
import org.springframework.stereotype.Component;

@Component
public class UserContextRoleMapper {

    public UserContextRoleDTO toDTO(UserContextRole entity) {
        if (entity == null) return null;

        return new UserContextRoleDTO(
                entity.getId(),
                entity.getUser() != null ? entity.getUser().getId() : null,
                entity.getRole() != null ? entity.getRole().name() : null,
                entity.getInstitution() != null ? entity.getInstitution().getId() : null,
                entity.getUnit() != null ? entity.getUnit().getId() : null,
                entity.getDepartment() != null ? entity.getDepartment().getId() : null,
                entity.getSector() != null ? entity.getSector().getId() : null
        );
    }

    public UserContextRole toEntity(
            UserContextRoleDTO dto,
            User user,
            Institution institution,
            Unit unit,
            Department department,
            Sector sector
    ) {
        if (dto == null) return null;

        return UserContextRole.Builder.anUserContextRole()
                .id(dto.id())
                .user(user)
                .role(dto.role() != null ? UserRole.valueOf(dto.role()) : null)
                .institution(institution)
                .unit(unit)
                .department(department)
                .sector(sector)
                .build();
    }
}
