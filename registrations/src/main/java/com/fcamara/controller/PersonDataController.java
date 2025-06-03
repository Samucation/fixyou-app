package com.fcamara.controller;

import com.fcamara.dto.PersonDataDTO;
import com.fcamara.service.PersonDataService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/person-data")
public class PersonDataController {

    private final PersonDataService service;

    public PersonDataController(PersonDataService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<PersonDataDTO>> getAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PersonDataDTO> getById(@PathVariable Long id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/cpf/{cpf}")
    public ResponseEntity<PersonDataDTO> getByCpf(@PathVariable String cpf) {
        return service.findByCpf(cpf)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/cnpj/{cnpj}")
    public ResponseEntity<PersonDataDTO> getByCnpj(@PathVariable String cnpj) {
        return service.findByCnpj(cnpj)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<PersonDataDTO> create(@RequestBody PersonDataDTO dto) {
        return ResponseEntity.ok(service.save(dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
