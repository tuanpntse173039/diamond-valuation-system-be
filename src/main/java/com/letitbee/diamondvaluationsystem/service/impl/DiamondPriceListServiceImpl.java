package com.letitbee.diamondvaluationsystem.service.impl;

import com.letitbee.diamondvaluationsystem.entity.DiamondPriceList;
import com.letitbee.diamondvaluationsystem.enums.*;
import com.letitbee.diamondvaluationsystem.exception.APIException;
import com.letitbee.diamondvaluationsystem.payload.DiamondPriceListDTO;
import com.letitbee.diamondvaluationsystem.repository.DiamondPriceListRepository;
import com.letitbee.diamondvaluationsystem.service.DiamondPriceListService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DiamondPriceListServiceImpl implements DiamondPriceListService {

    private DiamondPriceListRepository diamondPriceListRepository;

    private ModelMapper mapper;

    public DiamondPriceListServiceImpl(DiamondPriceListRepository diamondPriceListRepository, ModelMapper mapper) {
        this.diamondPriceListRepository = diamondPriceListRepository;
        this.mapper = mapper;
    }


    @Override
    public DiamondPriceListDTO createDiamondPriceList(DiamondPriceListDTO diamondPriceListDTO) {
        DiamondPriceList diamondPriceList = mapToEntity(diamondPriceListDTO);
        return mapToDto(diamondPriceListRepository.save(diamondPriceList));
    }

    @Override
    public DiamondPriceListDTO getDiamondPriceListByField(DiamondPriceListDTO diamondPriceListDTO) {
        List<DiamondPriceList> field = diamondPriceListRepository.findSelectedFieldsByDiamondProperties(
                diamondPriceListDTO.getDiamondOrigin(),
                diamondPriceListDTO.getCaratWeight(),
                diamondPriceListDTO.getColor() ,
                diamondPriceListDTO.getClarity(),
                diamondPriceListDTO.getCut(),
                diamondPriceListDTO.getPolish(),
                diamondPriceListDTO.getSymmetry(),
                diamondPriceListDTO.getShape(),
                diamondPriceListDTO.getFluorescence());

        if (field != null && !field.isEmpty()) {
            DiamondPriceListDTO diamondPriceList = field.stream().findFirst().map(this::mapToDto).get();
            return diamondPriceList;
        }
        else throw new APIException(HttpStatus.NOT_FOUND,"No diamond price list data found");
    }

    private DiamondPriceListDTO mapToDto(DiamondPriceList field) {
        return mapper.map(field, DiamondPriceListDTO.class);
    }
    private DiamondPriceList mapToEntity(DiamondPriceListDTO diamondPriceListDTO) {
        return mapper.map(diamondPriceListDTO, DiamondPriceList.class);
    }


}
