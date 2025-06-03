package com.fcamara.builder;

import com.fcamara.dto.PersonDataDTO;
import com.fcamara.model.ContractType;
import com.fcamara.model.PersonData;
import com.fcamara.model.Profile;
import org.springframework.stereotype.Component;

@Component
public class PersonDataMapper {

    public PersonDataDTO toDTO(PersonData personData) {
        if (personData == null) return null;

        return new PersonDataDTO(
                personData.getId(),
                personData.getContractType() != null ? personData.getContractType().name() : null,
                personData.getCpf(),
                personData.getCnpj(),
                personData.getRg(),
                personData.getCnh(),
                personData.getProfile() != null ? personData.getProfile().getId() : null
        );
    }

    public PersonData toEntity(PersonDataDTO dto, Profile profile) {
        if (dto == null) return null;

        return PersonData.Builder.aPersonData()
                .id(dto.id())
                .contractType(dto.contractType() != null ? ContractType.valueOf(dto.contractType()) : null)
                .cpf(dto.cpf())
                .cnpj(dto.cnpj())
                .rg(dto.rg())
                .cnh(dto.cnh())
                .profile(profile)
                .build();
    }
}

