package com.fcamara.controller;

import com.fcamara.model.Profile;
import com.fcamara.model.User;
import com.fcamara.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @PreAuthorize("hasRole('CREATE_USER')")
    @GetMapping("/{username}")
    public ResponseEntity<User> getUserByUsername(@PathVariable String username) {
        Optional<User> user = userService.findByUsername(username);
        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/profile")
    public ResponseEntity<List<User>> getUsersByProfileType(@RequestParam String type) {
        List<User> users = userService.findByProfileType(type);
        return ResponseEntity.ok(users);
    }

    @PreAuthorize("hasRole('LIST_USERS')")
    @GetMapping
    public ResponseEntity<List<User>> getAllUsersOrderedByUsername() {
        List<User> users = userService.findAllOrderedByUsername();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/count")
    public ResponseEntity<Long> countUsersByProfile(@RequestBody Profile profile) {
        Long count = userService.countByProfile(profile);
        return ResponseEntity.ok(count);
    }

    @GetMapping("/debug/roles")
    public ResponseEntity<?> getRoles() {
        return ResponseEntity.ok(
                SecurityContextHolder.getContext().getAuthentication().getAuthorities()
        );
    }


}
