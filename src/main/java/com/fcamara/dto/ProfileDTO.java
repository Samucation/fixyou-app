package com.fcamara.dto;

public record ProfileDTO(
        Long id,
        String unit,
        String department,
        String preferredShift,
        String jobTitle
) {
}