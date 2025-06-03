package com.fcamara.service;

import com.fcamara.dto.UserContextRoleDTO;
import com.fcamara.builder.UserContextRoleMapper;
import com.fcamara.model.*;
import com.fcamara.repository.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserContextRoleService {

    private final UserContextRoleRepository repository;
    private final UserContextRoleMapper mapper;
    private final UserRepository userRepository;
    private final InstitutionRepository institutionRepository;
    private final UnitRepository unitRepository;
    private final DepartmentRepository departmentRepository;
    private final SectorRepository sectorRepository;

    public UserContextRoleService(UserContextRoleRepository repository,
                                  UserContextRoleMapper mapper,
                                  UserRepository userRepository,
                                  InstitutionRepository institutionRepository,
                                  UnitRepository unitRepository,
                                  DepartmentRepository departmentRepository,
                                  SectorRepository sectorRepository) {
        this.repository = repository;
        this.mapper = mapper;
        this.userRepository = userRepository;
        this.institutionRepository = institutionRepository;
        this.unitRepository = unitRepository;
        this.departmentRepository = departmentRepository;
        this.sectorRepository = sectorRepository;
    }

    public List<UserContextRoleDTO> findAll() {
        return repository.findAll().stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    public Optional<UserContextRoleDTO> findById(Long id) {
        return repository.findById(id).map(mapper::toDTO);
    }

    public UserContextRoleDTO save(UserContextRoleDTO dto) {
        User user = userRepository.findById(dto.userId())
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + dto.userId()));

        Institution institution = dto.institutionId() != null
                ? institutionRepository.findById(dto.institutionId())
                .orElseThrow(() -> new IllegalArgumentException("Institution not found: " + dto.institutionId()))
                : null;

        Unit unit = dto.unitId() != null
                ? unitRepository.findById(dto.unitId())
                .orElseThrow(() -> new IllegalArgumentException("Unit not found: " + dto.unitId()))
                : null;

        Department department = dto.departmentId() != null
                ? departmentRepository.findById(dto.departmentId())
                .orElseThrow(() -> new IllegalArgumentException("Department not found: " + dto.departmentId()))
                : null;

        Sector sector = dto.sectorId() != null
                ? sectorRepository.findById(dto.sectorId())
                .orElseThrow(() -> new IllegalArgumentException("Sector not found: " + dto.sectorId()))
                : null;

        UserContextRole entity = mapper.toEntity(dto, user, institution, unit, department, sector);
        UserContextRole saved = repository.save(entity);
        return mapper.toDTO(saved);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
