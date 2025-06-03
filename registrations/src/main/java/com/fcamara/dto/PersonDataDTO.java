package com.fcamara.dto;

public record PersonDataDTO(
        Long id,
        String contractType,
        String cpf,
        String cnpj,
        String rg,
        String cnh,
        Long profileId
) {}
