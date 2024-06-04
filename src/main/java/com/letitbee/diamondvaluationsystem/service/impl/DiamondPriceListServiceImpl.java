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
    public DiamondPriceListDTO getDiamondPriceListByField(
            DiamondOrigin diamondOrigin,
            float caratWeight,
            Color color,
            Clarity clarity,
            Cut cut,
            Polish polish,
            Symmetry symmetry,
            Shape shape,
            Fluorescence fluorescence
    ) {
        List<DiamondPriceList> field = diamondPriceListRepository.findSelectedFieldsByDiamondProperties(
                diamondOrigin,
                caratWeight,
                color,
                clarity,
                cut,
                polish,
                symmetry,
                shape,
                fluorescence);
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
