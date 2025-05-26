package com.fcamara.controller;

import com.fcamara.model.Profile;
import com.fcamara.model.User;
import com.fcamara.model.UserRole;
import com.fcamara.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PreAuthorize("hasRole('GET_USERS_BY_USERNAME')")
    @GetMapping("/{username}")
    public ResponseEntity<User> getUserByUsername(@PathVariable String username) {
        Optional<User> user = userService.findByUsername(username);
        return user.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PreAuthorize("hasRole('GET_USER_BY_ROLE')")
    @GetMapping("/by-role")
    public ResponseEntity<List<User>> getUsersByRole(@RequestParam UserRole role) {
        List<User> users = userService.findByRole(role);
        return ResponseEntity.ok(users);
    }

    @PreAuthorize("hasRole('ALL_USERS_ODER_BY_USERNAME')")
    @GetMapping
    public ResponseEntity<List<User>> getAllUsersOrderedByUsername() {
        List<User> users = userService.findAllOrderedByUsername();
        return ResponseEntity.ok(users);
    }

    @PreAuthorize("hasRole('USERS_BY_PROFILE')")
    @PostMapping("/count-by-profile")
    public ResponseEntity<Long> countUsersByProfile(@RequestBody Profile profile) {
        Long count = userService.countByProfile(profile);
        return ResponseEntity.ok(count);
    }

    @PreAuthorize("hasRole('USERS_BY_PROFILE')")
    @PostMapping("/by-profile")
    public ResponseEntity<List<User>> getUsersByProfile(@RequestBody Profile profile) {
        List<User> users = userService.findByProfile(profile);
        return ResponseEntity.ok(users);
    }

    @PreAuthorize("hasRole('USERS_BY_UNITY')")
    @GetMapping("/by-unit")
    public ResponseEntity<List<User>> getUsersByUnit(@RequestParam String unit) {
        List<User> users = userService.findByUnit(unit);
        return ResponseEntity.ok(users);
    }

    @PreAuthorize("hasRole('USERS_BY_DEPARTMENT')")
    @GetMapping("/by-department")
    public ResponseEntity<List<User>> getUsersByDepartment(@RequestParam String department) {
        List<User> users = userService.findByDepartment(department);
        return ResponseEntity.ok(users);
    }


    @GetMapping("/debug/roles")
    public ResponseEntity<?> getCurrentUserRoles() {
        return ResponseEntity.ok(
                SecurityContextHolder.getContext().getAuthentication().getAuthorities()
        );
    }

}
