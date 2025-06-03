package com.fcamara.service;

import com.fcamara.dto.DepartmentTypeDTO;
import com.fcamara.repository.DepartmentTypeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentTypeService {

    private final DepartmentTypeRepository repository;

    public DepartmentTypeService(DepartmentTypeRepository repository) {
        this.repository = repository;
    }

    public List<DepartmentTypeDTO> findForInstitution(Long institutionId) {
        return repository.findAllByInstitutionOrGlobal(institutionId).stream()
                .map(d -> new DepartmentTypeDTO(d.getId(), d.getName(),
                        d.getInstitution() != null ? d.getInstitution().getId() : null))
                .toList();
    }
}