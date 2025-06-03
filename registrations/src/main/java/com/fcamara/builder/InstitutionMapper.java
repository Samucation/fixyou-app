package com.fcamara.builder;

import com.fcamara.dto.InstitutionDTO;
import com.fcamara.model.Institution;
import com.fcamara.model.User;
import org.springframework.stereotype.Component;

@Component
public class InstitutionMapper {

    public InstitutionDTO toDTO(Institution institution) {
        if (institution == null) return null;

        return new InstitutionDTO(
                institution.getId(),
                institution.getName(),
                institution.getCnpj(),
                institution.getDescription(),
                institution.getCreatedBy() != null ? institution.getCreatedBy().getId() : null
        );
    }

    public Institution toEntity(InstitutionDTO dto, User createdBy) {
        if (dto == null) return null;

        return Institution.Builder.anInstitution()
                .id(dto.id())
                .name(dto.name())
                .cnpj(dto.cnpj())
                .description(dto.description())
                .createdBy(createdBy)
                .build();
    }
}
