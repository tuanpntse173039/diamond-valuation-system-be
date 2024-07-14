package com.letitbee.diamondvaluationsystem.config;

import com.letitbee.diamondvaluationsystem.entity.DiamondValuationAssign;
import com.letitbee.diamondvaluationsystem.entity.ValuationRequest;
import com.letitbee.diamondvaluationsystem.payload.DiamondImageDTO;
import com.letitbee.diamondvaluationsystem.payload.DiamondValuationAssignDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.modelmapper.PropertyMap;

import com.letitbee.diamondvaluationsystem.entity.DiamondValuationNote;
import com.letitbee.diamondvaluationsystem.entity.ValuationRequestDetail;
import com.letitbee.diamondvaluationsystem.payload.DiamondValuationNoteDTO;
import com.letitbee.diamondvaluationsystem.payload.ValuationRequestDetailDTO;

import java.util.stream.Collectors;
@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        // Mapping for DiamondValuationNote to DiamondValuationNoteDTO
        modelMapper.addMappings(new PropertyMap<DiamondValuationNote, DiamondValuationNoteDTO>() {
            @Override
            protected void configure() {
                map(source.getCutScore()).setCutScore(0.0f); // Thay 0.0f bằng giá trị mặc định bạn muốn
                map().setDiamondImages(source.getDiamondImage().stream()
                        .map(diamondImage -> modelMapper.map(diamondImage, DiamondImageDTO.class))
                        .collect(Collectors.toSet()));
            }
        });

        // Mapping for ValuationRequestDetail to ValuationRequestDetailDTO
        modelMapper.addMappings(new PropertyMap<ValuationRequestDetail, ValuationRequestDetailDTO>() {
            @Override
            protected void configure() {
                map(source.getDiamondValuationNote()).setDiamondValuationNote(modelMapper.map(source.getDiamondValuationNote(), DiamondValuationNoteDTO.class));
                map().setDiamondValuationAssign(modelMapper.map(source.getDiamondValuationAssign(), DiamondValuationAssignDTO.class));
                map().setValuationRequestID(source.getValuationRequest().getId());
                map().setDiamondValuationAssigns(source.getDiamondValuationAssigns().stream()
                        .map(diamondValuationAssign -> modelMapper.map(diamondValuationAssign, DiamondValuationAssignDTO.class))
                        .collect(Collectors.toSet()));
            }
        });

        return modelMapper;
    }
}

