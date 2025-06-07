package com.fcamara.dto;

import java.util.UUID;

public record ProfilePreferencesDTO(
        UUID id,
        UUID profileId,
        UUID preferenceId,
        String value
) {}
