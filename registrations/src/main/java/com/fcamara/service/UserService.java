package com.fcamara.service;

import com.fcamara.dto.UserDTO;
import com.fcamara.builder.UserMapper;
import com.fcamara.model.Profile;
import com.fcamara.model.Role;
import com.fcamara.model.User;
import com.fcamara.model.UserRole;
import com.fcamara.repository.ProfileRepository;
import com.fcamara.repository.RoleRepository;
import com.fcamara.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final ProfileRepository profileRepository;
    private final RoleRepository roleRepository;

    public UserService(UserRepository userRepository,
                       UserMapper userMapper,
                       ProfileRepository profileRepository,
                       RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.profileRepository = profileRepository;
        this.roleRepository = roleRepository;
    }

    public List<UserDTO> findAll() {
        return userRepository.findAll().stream()
                .map(userMapper::toDTO)
                .collect(Collectors.toList());
    }

    public Optional<UserDTO> findById(Long id) {
        return userRepository.findById(id)
                .map(userMapper::toDTO);
    }

    public Optional<UserDTO> findByUsername(String username) {
        return userRepository.findByUsername(username)
                .map(userMapper::toDTO);
    }

    public Optional<UserDTO> findByKeycloakId(String keycloakId) {
        return userRepository.findByKeycloakId(keycloakId)
                .map(userMapper::toDTO);
    }

    public UserDTO save(UserDTO dto) {
        Profile profile = profileRepository.findById(dto.profileId())
                .orElseThrow(() -> new IllegalArgumentException("Profile not found: " + dto.profileId()));

        Set<Role> roles = dto.roleNames().stream()
                .map(roleName -> roleRepository.findByName(UserRole.valueOf(roleName))
                        .orElseThrow(() -> new IllegalArgumentException("Role not found: " + roleName)))
                .collect(Collectors.toSet());

        User user = userMapper.toEntity(dto, profile, roles);
        User saved = userRepository.save(user);
        return userMapper.toDTO(saved);
    }


    public void delete(Long id) {
        userRepository.deleteById(id);
    }
}
