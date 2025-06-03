package com.fcamara.service;

import com.fcamara.dto.ProfileDTO;
import com.fcamara.builder.ProfileMapper;
import com.fcamara.model.Profile;
import com.fcamara.model.Unit;
import com.fcamara.repository.ProfileRepository;
import com.fcamara.repository.UnitRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProfileService {

    private final ProfileRepository profileRepository;
    private final ProfileMapper profileMapper;
    private final UnitRepository unitRepository;

    public ProfileService(ProfileRepository profileRepository,
                          ProfileMapper profileMapper,
                          UnitRepository unitRepository) {
        this.profileRepository = profileRepository;
        this.profileMapper = profileMapper;
        this.unitRepository = unitRepository;
    }

    public List<ProfileDTO> findAll() {
        return profileRepository.findAll()
                .stream()
                .map(profileMapper::toDTO)
                .toList();
    }

    public Optional<ProfileDTO> findById(Long id) {
        return profileRepository.findById(id)
                .map(profileMapper::toDTO);
    }

    public Optional<ProfileDTO> findByJobTitle(String jobTitle) {
        return profileRepository.findByJobTitle(jobTitle)
                .map(profileMapper::toDTO);
    }

    public ProfileDTO save(ProfileDTO dto) {
        Unit unit = unitRepository.findById(dto.unitId())
                .orElseThrow(() -> new IllegalArgumentException("Unit not found: " + dto.unitId()));

        Profile profile = profileMapper.toEntity(dto, unit);
        Profile saved = profileRepository.save(profile);
        return profileMapper.toDTO(saved);
    }

    public void delete(Long id) {
        profileRepository.deleteById(id);
    }
}
