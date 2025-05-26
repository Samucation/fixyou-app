package com.fcamara.service;

import com.fcamara.model.Profile;
import com.fcamara.model.User;
import com.fcamara.model.UserRole;
import com.fcamara.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<User> findByUsername(String username) {
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

    public List<User> findByUnit(String unit) {
        return userRepository.findByProfile_Unit(unit);
    }

    public List<User> findByDepartment(String department) {
        return userRepository.findByProfile_Department(department);
    }

}
