package com.fcamara.dto;

import java.util.UUID;

public record LoginDTO(
        UUID id,
        String username,
        String keycloakId
) {}
