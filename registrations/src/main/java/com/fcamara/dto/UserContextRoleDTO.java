package com.fcamara.dto;

public record UserContextRoleDTO(
        Long id,
        Long userId,
        String role,
        Long institutionId,
        Long unitId,
        Long departmentId,
        Long sectorId
) {}
