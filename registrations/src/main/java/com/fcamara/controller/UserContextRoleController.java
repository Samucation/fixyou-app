package com.fcamara.controller;

import com.fcamara.dto.UserContextRoleDTO;
import com.fcamara.service.UserContextRoleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user-context-roles")
public class UserContextRoleController {

    private final UserContextRoleService service;

    public UserContextRoleController(UserContextRoleService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<UserContextRoleDTO>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserContextRoleDTO> findById(@PathVariable Long id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<UserContextRoleDTO> save(@RequestBody UserContextRoleDTO dto) {
        return ResponseEntity.ok(service.save(dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
