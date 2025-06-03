package com.fcamara.service;

import com.fcamara.dto.PersonDataDTO;
import com.fcamara.builder.PersonDataMapper;
import com.fcamara.model.PersonData;
import com.fcamara.model.Profile;
import com.fcamara.repository.PersonDataRepository;
import com.fcamara.repository.ProfileRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PersonDataService {

    private final PersonDataRepository personDataRepository;
    private final PersonDataMapper personDataMapper;
    private final ProfileRepository profileRepository;

    public PersonDataService(PersonDataRepository personDataRepository,
                             PersonDataMapper personDataMapper,
                             ProfileRepository profileRepository) {
        this.personDataRepository = personDataRepository;
        this.personDataMapper = personDataMapper;
        this.profileRepository = profileRepository;
    }

    public List<PersonDataDTO> findAll() {
        return personDataRepository.findAll()
                .stream()
                .map(personDataMapper::toDTO)
                .toList();
    }

    public Optional<PersonDataDTO> findById(Long id) {
        return personDataRepository.findById(id)
                .map(personDataMapper::toDTO);
    }

    public Optional<PersonDataDTO> findByCpf(String cpf) {
        return personDataRepository.findByCpf(cpf)
                .map(personDataMapper::toDTO);
    }

    public Optional<PersonDataDTO> findByCnpj(String cnpj) {
        return personDataRepository.findByCnpj(cnpj)
                .map(personDataMapper::toDTO);
    }

    public PersonDataDTO save(PersonDataDTO dto) {
        Profile profile = profileRepository.findById(dto.profileId())
                .orElseThrow(() -> new IllegalArgumentException("Profile not found: " + dto.profileId()));

        PersonData personData = personDataMapper.toEntity(dto, profile);
        PersonData saved = personDataRepository.save(personData);
        return personDataMapper.toDTO(saved);
    }

    public void delete(Long id) {
        personDataRepository.deleteById(id);
    }
}
