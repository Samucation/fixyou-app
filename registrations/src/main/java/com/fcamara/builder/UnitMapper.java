package com.fcamara.builder;

import com.fcamara.dto.UnitDTO;
import com.fcamara.model.Institution;
import com.fcamara.model.Unit;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UnitMapper {

    public UnitDTO toDTO(Unit unit) {
        if (unit == null) return null;

        return new UnitDTO(
                unit.getId(),
                unit.getName(),
                unit.getCode(),
                unit.getAddress(),
                unit.getCity(),
                unit.getState(),
                unit.getZipCode(),
                unit.getInstitution() != null ? unit.getInstitution().getId() : null
        );
    }

    public Unit toEntity(UnitDTO dto, Institution institution) {
        if (dto == null) return null;

        return Unit.Builder.anUnity()
                .id(dto.id())
                .name(dto.name())
                .code(dto.code())
                .address(dto.address())
                .city(dto.city())
                .state(dto.state())
                .zipCode(dto.zipCode())
                .institution(institution)
                .build();
    }

    public List<UnitDTO> toDTOList(List<Unit> units) {
        return units.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
}
