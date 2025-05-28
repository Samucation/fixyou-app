package com.fcamara.builder;

import com.fcamara.dto.BranchDTO;
import com.fcamara.model.Branch;
import com.fcamara.model.Company;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class BranchMapper {

    public BranchDTO toDTO(Branch branch) {
        return new BranchDTO(
                branch.getId(),
                branch.getName(),
                branch.getCode(),
                branch.getAddress(),
                branch.getCity(),
                branch.getState(),
                branch.getZipCode(),
                branch.getCompany().getId()
        );
    }

    public Branch toEntity(BranchDTO dto, Company company) {
        return new Branch(
                dto.id(),
                dto.name(),
                dto.code(),
                dto.address(),
                dto.city(),
                dto.state(),
                dto.zipCode(),
                company
        );
    }

    public List<BranchDTO> toDTOList(List<Branch> branches) {
        return branches.stream().map(this::toDTO).collect(Collectors.toList());
    }
}
