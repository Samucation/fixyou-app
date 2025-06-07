package com.fcamara.dto;

import java.time.LocalDate;

public record UserRegistrationRequestDTO(
        String username,
        String personId,  // UUID como String
        String position,
        String contract,
        LocalDate startDate,
        boolean termLgpd,
        boolean hasAdmin,
        boolean hasMobile,
        boolean hasManager
) {}
