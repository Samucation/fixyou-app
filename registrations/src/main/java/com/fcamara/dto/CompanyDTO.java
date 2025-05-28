package com.fcamara.dto;

public record CompanyDTO(
        Long id,
        String name,
        String cnpj,
        String description
) {}