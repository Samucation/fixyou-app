package com.fcamara.service;

import com.fcamara.dto.BranchDTO;
import com.fcamara.exception.ResourceNotFoundException;
import com.fcamara.builder.BranchMapper;
import com.fcamara.model.Branch;
import com.fcamara.model.Company;
import com.fcamara.repository.BranchRepository;
import com.fcamara.repository.CompanyRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BranchService {

    private static final Logger LOGGER = LogManager.getLogger(BranchService.class);

    private final BranchRepository branchRepository;
    private final CompanyRepository companyRepository;
    private final BranchMapper branchMapper;

    public BranchService(BranchRepository branchRepository, CompanyRepository companyRepository, BranchMapper branchMapper) {
        this.branchRepository = branchRepository;
        this.companyRepository = companyRepository;
        this.branchMapper = branchMapper;
    }

    public BranchDTO createBranch(BranchDTO dto) {
        LOGGER.info("[CREATE_BRANCH] Creating branch with code [{}]", dto.code());
        Company company = companyRepository.findById(dto.companyId())
                .orElseThrow(() -> {
                    LOGGER.error("[CREATE_BRANCH] Company not found with ID [{}]", dto.companyId());
                    return new ResourceNotFoundException("Company not found with ID: " + dto.companyId());
                });

        Branch branch = branchMapper.toEntity(dto, company);
        branch = branchRepository.save(branch);

        LOGGER.info("[CREATE_BRANCH] Branch created with ID [{}]", branch.getId());
        return branchMapper.toDTO(branch);
    }

    public List<BranchDTO> getAllBranches() {
        LOGGER.info("[GET_ALL_BRANCHES] Fetching all branches.");
        return branchMapper.toDTOList(branchRepository.findAll());
    }

    public BranchDTO getBranchById(Long id) {
        LOGGER.info("[GET_BRANCH_BY_ID] Fetching branch with ID [{}]", id);
        Branch branch = branchRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Branch not found with ID: " + id));
        return branchMapper.toDTO(branch);
    }

    public BranchDTO getBranchByCode(String code) {
        LOGGER.info("[GET_BRANCH_BY_CODE] Fetching branch with code [{}]", code);
        Branch branch = branchRepository.findByCode(code)
                .orElseThrow(() -> new ResourceNotFoundException("Branch not found with code: " + code));
        return branchMapper.toDTO(branch);
    }

    public List<BranchDTO> getBranchesByCompany(Long companyId) {
        LOGGER.info("[GET_BRANCHES_BY_COMPANY] Fetching branches for company ID [{}]", companyId);
        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new ResourceNotFoundException("Company not found with ID: " + companyId));

        return branchMapper.toDTOList(branchRepository.findByCompany(company));
    }

    public void deleteBranch(Long id) {
        LOGGER.info("[DELETE_BRANCH] Deleting branch with ID [{}]", id);
        Branch branch = branchRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Branch not found with ID: " + id));
        branchRepository.delete(branch);
    }
}
