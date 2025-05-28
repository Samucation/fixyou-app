package com.fcamara.builder;

import com.fcamara.dto.CompanyDTO;
import com.fcamara.model.Company;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CompanyMapper {

    public CompanyDTO toDTO(Company company) {
        return new CompanyDTO(
                company.getId(),
                company.getName(),
                company.getCnpj(),
                company.getDescription()
        );
    }

    public Company toEntity(CompanyDTO dto) {
        return new Company(
                dto.id(),
                dto.name(),
                dto.cnpj(),
                dto.description()
        );
    }

    public List<CompanyDTO> toDTOList(List<Company> companies) {
        return companies.stream().map(this::toDTO).collect(Collectors.toList());
    }
}
