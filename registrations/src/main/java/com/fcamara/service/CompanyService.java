package com.fcamara.service;


import com.fcamara.dto.CompanyDTO;
import com.fcamara.exception.ResourceNotFoundException;
import com.fcamara.builder.CompanyMapper;
import com.fcamara.model.Company;
import com.fcamara.repository.CompanyRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyService {

    private static final Logger LOGGER = LogManager.getLogger(CompanyService.class);

    private final CompanyRepository companyRepository;
    private final CompanyMapper companyMapper;

    public CompanyService(CompanyRepository companyRepository, CompanyMapper companyMapper) {
        this.companyRepository = companyRepository;
        this.companyMapper = companyMapper;
    }

    public CompanyDTO createCompany(CompanyDTO dto) {
        LOGGER.info("[CREATE_COMPANY] Creating company with CNPJ [{}]", dto.cnpj());
        Company company = companyMapper.toEntity(dto);
        company = companyRepository.save(company);
        LOGGER.info("[CREATE_COMPANY] Company created with ID [{}]", company.getId());
        return companyMapper.toDTO(company);
    }

    public List<CompanyDTO> getAllCompanies() {
        LOGGER.info("[GET_ALL_COMPANIES] Fetching all companies.");
        return companyMapper.toDTOList(companyRepository.findAll());
    }

    public CompanyDTO getCompanyById(Long id) {
        LOGGER.info("[GET_COMPANY_BY_ID] Fetching company with ID [{}]", id);
        Company company = companyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Company not found with ID: " + id));
        return companyMapper.toDTO(company);
    }

    public CompanyDTO getCompanyByCnpj(String cnpj) {
        LOGGER.info("[GET_COMPANY_BY_CNPJ] Fetching company with CNPJ [{}]", cnpj);
        Company company = companyRepository.findByCnpj(cnpj)
                .orElseThrow(() -> new ResourceNotFoundException("Company not found with CNPJ: " + cnpj));
        return companyMapper.toDTO(company);
    }

    public void deleteCompany(Long id) {
        LOGGER.info("[DELETE_COMPANY] Deleting company with ID [{}]", id);
        Company company = companyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Company not found with ID: " + id));
        companyRepository.delete(company);
    }
}
