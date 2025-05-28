package com.fcamara.dto;

import java.util.Set;

public record ProfileDTO(
        Long id,
        Long branchId,
        String branchName,
        Set<Long> departmentIds,
        String preferredShift,
        String jobTitle
) {
}