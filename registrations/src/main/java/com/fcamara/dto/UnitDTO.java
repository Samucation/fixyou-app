package com.fcamara.dto;

public record UnitDTO(
        Long id,
        String name,
        String code,
        String address,
        String city,
        String state,
        String zipCode,
        Long institutionId
) {}
