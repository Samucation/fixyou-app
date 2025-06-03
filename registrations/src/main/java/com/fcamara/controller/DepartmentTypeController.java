package com.fcamara.controller;

import com.fcamara.dto.DepartmentTypeDTO;
import com.fcamara.service.DepartmentTypeService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/department-types")
public class DepartmentTypeController {

    private final DepartmentTypeService service;

    public DepartmentTypeController(DepartmentTypeService service) {
        this.service = service;
    }

    @GetMapping("/visible/{institutionId}")
    public List<DepartmentTypeDTO> getVisibleTypes(@PathVariable Long institutionId) {
        return service.findForInstitution(institutionId);
    }

}
