package com.fcamara.controller;

import com.fcamara.dto.BranchDTO;
import com.fcamara.service.BranchService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/branches")
public class BranchController {

    private final BranchService branchService;

    public BranchController(BranchService branchService) {
        this.branchService = branchService;
    }

    @PostMapping
    public ResponseEntity<BranchDTO> create(@RequestBody BranchDTO dto) {
        return ResponseEntity.ok(branchService.createBranch(dto));
    }

    @GetMapping
    public ResponseEntity<List<BranchDTO>> getAll() {
        return ResponseEntity.ok(branchService.getAllBranches());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BranchDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(branchService.getBranchById(id));
    }

    @GetMapping("/by-code")
    public ResponseEntity<BranchDTO> getByCode(@RequestParam String code) {
        return ResponseEntity.ok(branchService.getBranchByCode(code));
    }

    @GetMapping("/by-company/{companyId}")
    public ResponseEntity<List<BranchDTO>> getByCompany(@PathVariable Long companyId) {
        return ResponseEntity.ok(branchService.getBranchesByCompany(companyId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        branchService.deleteBranch(id);
        return ResponseEntity.noContent().build();
    }
}
