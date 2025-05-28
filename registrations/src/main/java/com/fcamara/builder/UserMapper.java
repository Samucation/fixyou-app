package com.fcamara.builder;

import com.fcamara.dto.ProfileDTO;
import com.fcamara.dto.UserDTO;
import com.fcamara.model.User;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserMapper {

    public UserDTO toDTO(User user) {
        return new UserDTO(
                user.getId(),
                user.getUsername(),
                user.getRoles().stream()
                        .map(role -> role.getName().name())
                        .collect(Collectors.toSet()),
                new ProfileDTO(
                        user.getProfile().getId(),
                        user.getProfile().getBranch().getId(),
                        user.getProfile().getBranch().getName(),
                        user.getProfile().getDepartments().stream()
                                .map(dept -> dept.getId()) // Aqui é ID
                                .collect(Collectors.toSet()),
                        user.getProfile().getPreferredShift(),
                        user.getProfile().getJobTitle()
                )
        );
    }

    public List<UserDTO> toListDTO(List<User> users) {
        return users.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
}
