package com.fcamara.dto;


import java.util.Set;

public record UserDTO(
        Long id,
        String username,
        String keycloakId,
        Long profileId,
        Set<String> roleNames
) {}
