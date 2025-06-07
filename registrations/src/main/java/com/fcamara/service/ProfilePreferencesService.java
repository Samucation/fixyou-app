package com.fcamara.service;

import com.fcamara.builder.ProfilePreferencesMapper;
import com.fcamara.dto.ProfilePreferencesDTO;
import com.fcamara.model.ProfilePreferences;
import com.fcamara.repository.ProfilePreferencesRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ProfilePreferencesService {

    private final ProfilePreferencesRepository repository;
    private final ProfilePreferencesMapper mapper;

    public ProfilePreferencesService(ProfilePreferencesRepository repository,
                                     ProfilePreferencesMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public List<ProfilePreferencesDTO> findAll() {
        return repository.findAll().stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<ProfilePreferencesDTO> findByProfileId(UUID profileId) {
        return repository.findAllByProfileId(profileId).stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    public ProfilePreferencesDTO save(ProfilePreferencesDTO dto) {
        ProfilePreferences entity = mapper.toEntity(dto);
        if (entity.getId() == null) {
            entity.setId(UUID.randomUUID());
        }
        return mapper.toDTO(repository.save(entity));
    }

    public void delete(UUID id) {
        repository.deleteById(id);
    }
}
