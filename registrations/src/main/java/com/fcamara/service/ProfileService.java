package com.fcamara.service;

import com.fcamara.dto.ProfileDTO;
import com.fcamara.exception.ResourceNotFoundException;
import com.fcamara.builder.ProfileMapper;
import com.fcamara.model.Branch;
import com.fcamara.model.Department;
import com.fcamara.model.Profile;
import com.fcamara.repository.BranchRepository;
import com.fcamara.repository.DepartmentRepository;
import com.fcamara.repository.ProfileRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ProfileService {

    private static final Logger LOGGER = LogManager.getLogger(ProfileService.class);

    private final ProfileRepository profileRepository;
    private final BranchRepository branchRepository;
    private final DepartmentRepository departmentRepository;
    private final ProfileMapper profileMapper;

    public ProfileService(ProfileRepository profileRepository, BranchRepository branchRepository,
                          DepartmentRepository departmentRepository, ProfileMapper profileMapper) {
        this.profileRepository = profileRepository;
        this.branchRepository = branchRepository;
        this.departmentRepository = departmentRepository;
        this.profileMapper = profileMapper;
    }

    public ProfileDTO createProfile(ProfileDTO dto) {
        LOGGER.info("[CREATE_PROFILE] Creating profile for branch [{}]", dto.branchId());

        Branch branch = branchRepository.findById(dto.branchId())
                .orElseThrow(() -> new ResourceNotFoundException("Branch not found with ID: " + dto.branchId()));

        Set<Department> departments = dto.departmentIds().stream()
                .map(id -> departmentRepository.findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException("Department not found with ID: " + id)))
                .collect(Collectors.toSet());

        Profile profile = profileMapper.toEntity(dto, branch, departments);
        profile = profileRepository.save(profile);

        LOGGER.info("[CREATE_PROFILE] Profile created with ID [{}]", profile.getId());
        return profileMapper.toDTO(profile);
    }

    public List<ProfileDTO> getAllProfiles() {
        LOGGER.info("[GET_ALL_PROFILES] Fetching all profiles.");
        return profileMapper.toDTOList(profileRepository.findAll());
    }

    public ProfileDTO getProfileById(Long id) {
        LOGGER.info("[GET_PROFILE_BY_ID] Fetching profile with ID [{}]", id);
        Profile profile = profileRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Profile not found with ID: " + id));
        return profileMapper.toDTO(profile);
    }

    public List<ProfileDTO> getProfilesByBranch(Long branchId) {
        LOGGER.info("[GET_PROFILES_BY_BRANCH] Fetching profiles for branch [{}]", branchId);
        Branch branch = branchRepository.findById(branchId)
                .orElseThrow(() -> new ResourceNotFoundException("Branch not found with ID: " + branchId));

        return profileMapper.toDTOList(profileRepository.findByBranch(branch));
    }

    public void deleteProfile(Long id) {
        LOGGER.info("[DELETE_PROFILE] Deleting profile with ID [{}]", id);
        Profile profile = profileRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Profile not found with ID: " + id));
        profileRepository.delete(profile);
    }
}
