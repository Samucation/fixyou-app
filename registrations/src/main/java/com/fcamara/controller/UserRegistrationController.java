package com.fcamara.controller;

import com.fcamara.dto.UserRegistrationRequestDTO;
import com.fcamara.service.UserRegistrationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/usuarios")
public class UserRegistrationController {

    private final UserRegistrationService registrationService;

    public UserRegistrationController(UserRegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @PreAuthorize("hasRole('REGISTRATION_USER')")
    @PostMapping
    public ResponseEntity<String> registerUser(@RequestBody UserRegistrationRequestDTO dto) {
        registrationService.registerUser(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body("Usuário criado com sucesso.");
    }

}

