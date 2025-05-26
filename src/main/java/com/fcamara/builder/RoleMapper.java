package com.fcamara.builder;

import com.fcamara.dto.RoleDTO;
import com.fcamara.model.Role;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class RoleMapper {

    private final ModelMapper modelMapper;

    public RoleMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public RoleDTO toDTO(Role role) {
        return modelMapper.map(role, RoleDTO.class);
    }

    public Role toEntity(RoleDTO dto) {
        return modelMapper.map(dto, Role.class);
    }

    public List<RoleDTO> toListDTO(List<Role> roles) {
        return roles.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<Role> toListEntity(List<RoleDTO> dtos) {
        return dtos.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }
}
