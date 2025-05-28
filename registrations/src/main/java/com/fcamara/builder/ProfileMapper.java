package com.fcamara.builder;

import com.fcamara.dto.ProfileDTO;
import com.fcamara.model.Branch;
import com.fcamara.model.Department;
import com.fcamara.model.Profile;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class ProfileMapper {

    public ProfileDTO toDTO(Profile profile) {
        return new ProfileDTO(
                profile.getId(),
                profile.getBranch().getId(),
                profile.getBranch().getName(),
                profile.getDepartments().stream().map(Department::getId).collect(Collectors.toSet()),
                profile.getPreferredShift(),
                profile.getJobTitle()
        );
    }

    public Profile toEntity(ProfileDTO dto, Branch branch, Set<Department> departments) {
        return new Profile(
                dto.id(),
                branch,
                departments,
                dto.preferredShift(),
                dto.jobTitle()
        );
    }

    public List<ProfileDTO> toDTOList(List<Profile> profiles) {
        return profiles.stream().map(this::toDTO).collect(Collectors.toList());
    }
}
