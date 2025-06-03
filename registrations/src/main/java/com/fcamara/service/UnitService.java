package com.fcamara.service;

import com.fcamara.dto.UnitDTO;
import com.fcamara.exception.ResourceNotFoundException;
import com.fcamara.builder.UnitMapper;
import com.fcamara.model.Institution;
import com.fcamara.model.Unit;
import com.fcamara.repository.InstitutionRepository;
import com.fcamara.repository.UnitRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UnitService {

    private static final Logger LOGGER = LogManager.getLogger(UnitService.class);

    private final UnitRepository unitRepository;
    private final InstitutionRepository institutionRepository;
    private final UnitMapper unitMapper;

    public UnitService(UnitRepository unitRepository,
                       InstitutionRepository institutionRepository,
                       UnitMapper unitMapper) {
        this.unitRepository = unitRepository;
        this.institutionRepository = institutionRepository;
        this.unitMapper = unitMapper;
    }

    public UnitDTO create(UnitDTO dto) {
        LOGGER.info("[CREATE_UNIT] Creating unit with code [{}]", dto.code());

        Institution institution = institutionRepository.findById(dto.institutionId())
                .orElseThrow(() -> {
                    LOGGER.error("[CREATE_UNIT] Institution not found with ID [{}]", dto.institutionId());
                    return new ResourceNotFoundException("Institution not found with ID: " + dto.institutionId());
                });

        Unit unit = unitMapper.toEntity(dto, institution);
        unit = unitRepository.save(unit);

        LOGGER.info("[CREATE_UNIT] Unit created with ID [{}]", unit.getId());
        return unitMapper.toDTO(unit);
    }

    public List<UnitDTO> findAll() {
        LOGGER.info("[FIND_ALL_UNITS] Fetching all units.");
        return unitMapper.toDTOList(unitRepository.findAll());
    }

    public UnitDTO findById(Long id) {
        LOGGER.info("[FIND_UNIT_BY_ID] Fetching unit with ID [{}]", id);
        Unit unit = unitRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Unit not found with ID: " + id));
        return unitMapper.toDTO(unit);
    }

    public UnitDTO findByCode(String code) {
        LOGGER.info("[FIND_UNIT_BY_CODE] Fetching unit with code [{}]", code);
        Unit unit = unitRepository.findByCode(code)
                .orElseThrow(() -> new ResourceNotFoundException("Unit not found with code: " + code));
        return unitMapper.toDTO(unit);
    }

    public List<UnitDTO> findByInstitution(Long institutionId) {
        LOGGER.info("[FIND_UNITS_BY_INSTITUTION] Fetching units for institution ID [{}]", institutionId);

        Institution institution = institutionRepository.findById(institutionId)
                .orElseThrow(() -> new ResourceNotFoundException("Institution not found with ID: " + institutionId));

        return unitMapper.toDTOList(unitRepository.findByInstitution(institution));
    }

    public void delete(Long id) {
        LOGGER.info("[DELETE_UNIT] Deleting unit with ID [{}]", id);
        Unit unit = unitRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Unit not found with ID: " + id));
        unitRepository.delete(unit);
    }
}
