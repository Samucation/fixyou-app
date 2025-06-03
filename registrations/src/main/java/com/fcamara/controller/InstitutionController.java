package com.fcamara.controller;

import com.fcamara.dto.InstitutionDTO;
import com.fcamara.service.InstitutionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/institutions")
public class InstitutionController {

    private final InstitutionService service;

    public InstitutionController(InstitutionService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<InstitutionDTO>> getAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<InstitutionDTO> getById(@PathVariable Long id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<InstitutionDTO> create(@RequestBody InstitutionDTO dto) {
        return ResponseEntity.ok(service.save(dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
