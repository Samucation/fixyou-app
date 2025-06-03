package com.fcamara.service;

import com.fcamara.dto.InstitutionDTO;
import com.fcamara.builder.InstitutionMapper;
import com.fcamara.model.Institution;
import com.fcamara.model.User;
import com.fcamara.repository.InstitutionRepository;
import com.fcamara.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InstitutionService {

    private final InstitutionRepository institutionRepository;
    private final InstitutionMapper institutionMapper;
    private final UserRepository userRepository;

    public InstitutionService(InstitutionRepository institutionRepository,
                              InstitutionMapper institutionMapper,
                              UserRepository userRepository) {
        this.institutionRepository = institutionRepository;
        this.institutionMapper = institutionMapper;
        this.userRepository = userRepository;
    }

    public List<InstitutionDTO> findAll() {
        return institutionRepository.findAll()
                .stream()
                .map(institutionMapper::toDTO)
                .toList();
    }

    public Optional<InstitutionDTO> findById(Long id) {
        return institutionRepository.findById(id)
                .map(institutionMapper::toDTO);
    }

    public InstitutionDTO save(InstitutionDTO dto) {
        User createdBy = userRepository.findById(dto.createdBy())
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + dto.createdBy()));

        Institution entity = institutionMapper.toEntity(dto, createdBy);
        Institution saved = institutionRepository.save(entity);
        return institutionMapper.toDTO(saved);
    }

    public void deleteById(Long id) {
        institutionRepository.deleteById(id);
    }
}
