package com.fcamara.dto;

import com.fcamara.model.ContractType;
import com.fcamara.model.UserRole;
import java.util.Set;

public record UserRegistrationDTO(
        String username,
        String password,
        String email,
        String firstName,
        String lastName,
        ContractType contractType,
        String cpf,
        String cnpj,
        String rg,
        String cnh,
        Long branchId,
        Long profileId,
        Set<Long> departmentIds,
        String jobTitle,
        String preferredShift,
        Set<UserRole> roles
) {}

