package com.fcamara.builder;

import com.fcamara.dto.ProfileDTO;
import com.fcamara.model.Profile;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProfileMapper {

    private final ModelMapper modelMapper;

    public ProfileMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public ProfileDTO toDTO(Profile profile) {
        return modelMapper.map(profile, ProfileDTO.class);
    }

    public Profile toEntity(ProfileDTO dto) {
        return modelMapper.map(dto, Profile.class);
    }

    public List<ProfileDTO> toListDTO(List<Profile> profiles) {
        return profiles.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<Profile> toListEntity(List<ProfileDTO> dtos) {
        return dtos.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }
}
