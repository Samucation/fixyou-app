package com.fcamara.builder;

import com.fcamara.model.SampleModel;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import com.fcamara.dto.SampleDTO;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class SampleMapper {

    private final ModelMapper modelMapper;

    public SampleMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public SampleDTO toDTO(SampleModel model) {
        return modelMapper.map(model, SampleDTO.class);
    }

    public SampleModel toEntity(SampleDTO dto) {
        return modelMapper.map(dto, SampleModel.class);
    }

    public List<SampleDTO> toListDTO(List<SampleModel> modelList) {
        return modelList.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<SampleModel> toList(List<SampleDTO> dtosList) {
        return dtosList.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }
}
