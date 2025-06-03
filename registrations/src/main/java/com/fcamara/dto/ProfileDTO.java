package com.fcamara.dto;

public record ProfileDTO(
        Long id,
        String preferredShift,
        String jobTitle,
        Long unitId
) {}
