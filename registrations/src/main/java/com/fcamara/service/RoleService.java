package com.fcamara.service;

import com.fcamara.builder.RoleMapper;
import com.fcamara.dto.RoleDTO;
import com.fcamara.model.Role;
import com.fcamara.repository.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleService {

    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;

    public RoleService(RoleRepository roleRepository, RoleMapper roleMapper) {
        this.roleRepository = roleRepository;
        this.roleMapper = roleMapper;
    }

    public List<RoleDTO> findAll() {
        return roleRepository.findAll()
                .stream()
                .map(roleMapper::toDTO)
                .toList();
    }

    public Optional<RoleDTO> findById(Long id) {
        return roleRepository.findById(id)
                .map(roleMapper::toDTO);
    }

    public RoleDTO save(RoleDTO dto) {
        Role role = roleMapper.toEntity(dto);
        Role saved = roleRepository.save(role);
        return roleMapper.toDTO(saved);
    }

    public void deleteById(Long id) {
        roleRepository.deleteById(id);
    }
}
