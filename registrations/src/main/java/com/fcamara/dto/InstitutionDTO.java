package com.fcamara.dto;

public record InstitutionDTO(
        Long id,
        String name,
        String cnpj,
        String description,
        Long createdBy
) {}
