package com.fcamara.service;

import com.fcamara.builder.UserMapper;
import com.fcamara.dto.UserDTO;
import com.fcamara.dto.UserRegistrationDTO;
import com.fcamara.exception.ResourceNotFoundException;
import com.fcamara.exception.RoleNotFoundException;
import com.fcamara.exception.UserAlreadyExistsException;
import com.fcamara.model.*;
import com.fcamara.repository.*;
import com.fcamara.util.AuthUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService {

    private static final Logger LOGGER = LogManager.getLogger(UserService.class);
    private final UserRepository userRepository;
    private final ProfileRepository profileRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;
    private final PersonDataRepository personDataRepository;
    private final BranchRepository branchRepository;
    private final DepartmentRepository departmentRepository;

    public UserService(UserRepository userRepository, ProfileRepository profileRepository, RoleRepository roleRepository,
                       UserMapper userMapper, PersonDataRepository personDataRepository, BranchRepository branchRepository, DepartmentRepository departmentRepository) {
        this.userRepository = userRepository;
        this.profileRepository = profileRepository;
        this.roleRepository = roleRepository;
        this.userMapper = userMapper;
        this.personDataRepository = personDataRepository;
        this.branchRepository = branchRepository;
        this.departmentRepository = departmentRepository;
    }

    @Cacheable("user-find-user")
    public Optional<User> findByUsername(String username) {
        LOGGER.info(">>> [CACHE MISS] Chamando API sem redis, username: [{}]", username);
        return userRepository.findByUsername(username);
    }

    public List<User> findByRole(UserRole role) {
        return userRepository.findByRole(role);
    }

    public List<User> findAllOrderedByUsername() {
        return userRepository.findAllByOrderByUsernameAsc();
    }

    public Long countByProfile(Profile profile) {
        return userRepository.countByProfile(profile);
    }

    public List<User> findByProfile(Profile profile) {
        return userRepository.findByProfile(profile);
    }

    public Optional<User> findByKeycloakId(String keycloakId) {
        return userRepository.findByKeycloakId(keycloakId);
    }


    public UserDTO registerUser(UserRegistrationDTO dto) {
        LOGGER.info("[REGISTER_USER] >>> Starting user registration process for username [{}]", dto.username());

        String keycloakId = AuthUtils.getKeycloakId();
        String username = AuthUtils.getUsername();
        LOGGER.info("[REGISTER_USER] >>> Request made by admin [{}] with Keycloak ID [{}]", username, keycloakId);

        if (userRepository.findByKeycloakId(keycloakId).isPresent()) {
            LOGGER.warn("[REGISTER_USER] >>> User with Keycloak ID [{}] already exists.", keycloakId);
            throw new UserAlreadyExistsException("User with this Keycloak ID already exists.");
        }

        Branch branch = branchRepository.findById(dto.branchId())
                .orElseThrow(() -> {
                    LOGGER.error("[REGISTER_USER] >>> Branch not found with ID [{}]", dto.branchId());
                    return new ResourceNotFoundException("Branch not found with ID: " + dto.branchId());
                });
        LOGGER.info("[REGISTER_USER] >>> Branch found [{}]", branch.getName());

        Profile profile = profileRepository.findById(dto.profileId()).orElse(null);

        if (profile == null) {
            LOGGER.info("[REGISTER_USER] >>> Profile not provided. Creating new profile for branch [{}]", branch.getName());

            profile = new Profile();
            profile.setBranch(branch);
            profile.setJobTitle(dto.jobTitle());
            profile.setPreferredShift(dto.preferredShift());

            // 🔗 Fetch departments
            Set<Department> departments = dto.departmentIds().stream()
                    .map(deptId -> departmentRepository.findById(deptId)
                            .orElseThrow(() -> {
                                LOGGER.error("[REGISTER_USER] >>> Department not found [{}]", deptId);
                                return new ResourceNotFoundException("Department not found with ID: " + deptId);
                            }))
                    .collect(Collectors.toSet());

            profile.setDepartments(departments);

            profile = profileRepository.save(profile);
            LOGGER.info("[REGISTER_USER] >>> New profile created with ID [{}]", profile.getId());
        } else {
            LOGGER.info("[REGISTER_USER] >>> Using existing profile with ID [{}]", profile.getId());
        }

        // 🔗 Create PersonData
        LOGGER.info("[REGISTER_USER] >>> Creating PersonData for username [{}]", dto.username());
        PersonData personData = new PersonData();
        personData.setContractType(dto.contractType());
        personData.setCpf(dto.cpf());
        personData.setCnpj(dto.cnpj());
        personData.setRg(dto.rg());
        personData.setCnh(dto.cnh());
        personData.setProfile(profile);
        personDataRepository.save(personData);
        LOGGER.info("[REGISTER_USER] >>> PersonData saved successfully for [{}]", dto.username());

        // 🔗 Map Roles
        LOGGER.info("[REGISTER_USER] >>> Mapping roles for [{}]", dto.username());
        Set<Role> roles = dto.roles().stream()
                .map(roleEnum -> roleRepository.findByName(roleEnum)
                        .orElseThrow(() -> {
                            LOGGER.error("[REGISTER_USER] >>> Role not found [{}]", roleEnum);
                            return new RoleNotFoundException("Role not found: " + roleEnum);
                        }))
                .collect(Collectors.toSet());
        LOGGER.info("[REGISTER_USER] >>> Roles mapped [{}]", roles);

        // 🔗 Create User
        LOGGER.info("[REGISTER_USER] >>> Creating user entity [{}]", dto.username());
        User user = new User();
        user.setKeycloakId(keycloakId);
        user.setUsername(dto.username());
        user.setProfile(profile);
        user.setRoles(roles);

        userRepository.save(user);
        LOGGER.info("[REGISTER_USER] >>> User registered successfully with ID [{}]", user.getId());

        return userMapper.toDTO(user);
    }





}
