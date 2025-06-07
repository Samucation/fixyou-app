package com.fcamara.controller;

import com.fcamara.dto.ProfilePreferencesDTO;
import com.fcamara.service.ProfilePreferencesService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/profile-preferences")
public class ProfilePreferencesController {

    private final ProfilePreferencesService service;

    public ProfilePreferencesController(ProfilePreferencesService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<ProfilePreferencesDTO>> getAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/profile/{profileId}")
    public ResponseEntity<List<ProfilePreferencesDTO>> getByProfileId(@PathVariable UUID profileId) {
        return ResponseEntity.ok(service.findByProfileId(profileId));
    }

    @PostMapping
    public ResponseEntity<ProfilePreferencesDTO> create(@RequestBody ProfilePreferencesDTO dto) {
        return ResponseEntity.ok(service.save(dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
