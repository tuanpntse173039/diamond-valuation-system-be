package com.letitbee.diamondvaluationsystem.service.impl;

import com.letitbee.diamondvaluationsystem.enums.*;
import com.letitbee.diamondvaluationsystem.payload.DiamondPriceListDTO;
import com.letitbee.diamondvaluationsystem.repository.DiamondPriceListRepository;
import com.letitbee.diamondvaluationsystem.service.DiamondPriceListService;
import org.modelmapper.ModelMapper;
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
        return null;
    }

    @Override
    public DiamondPriceListDTO getDiamondPriceListByField(DiamondPriceListDTO diamondPriceListDTO) {
        List<Object[]> field = diamondPriceListRepository.findSelectedFieldsByDiamondProperties(
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
            DiamondPriceListDTO diamondPriceList = new DiamondPriceListDTO();
            diamondPriceList.setCreationDate((String) field.get(0)[0]);
            diamondPriceList.setFairPrice(String.valueOf((float) field.get(0)[1]));
            diamondPriceList.setMinPrice(String.valueOf((float) field.get(0)[2]));
            diamondPriceList.setMaxPrice(String.valueOf((float) field.get(0)[3]));
            diamondPriceList.setEffectDate((String) field.get(0)[4]);
            diamondPriceList.setDiamondOrigin(diamondPriceListDTO.getDiamondOrigin());
            diamondPriceList.setCaratWeight(diamondPriceListDTO.getCaratWeight());
            diamondPriceList.setColor(diamondPriceListDTO.getColor());
            diamondPriceList.setClarity(diamondPriceListDTO.getClarity());
            diamondPriceList.setCut(diamondPriceListDTO.getCut());
            diamondPriceList.setPolish(diamondPriceListDTO.getPolish());
            diamondPriceList.setSymmetry(diamondPriceListDTO.getSymmetry());
            diamondPriceList.setShape(diamondPriceListDTO.getShape());
            diamondPriceList.setFluorescence(diamondPriceListDTO.getFluorescence());
            return diamondPriceList;
        }
        return null;
    }

    private DiamondPriceListDTO mapToDto(Object[] field) {
        return mapper.map(field, DiamondPriceListDTO.class);
    }
    private Object[] mapToEntity(DiamondPriceListDTO diamondPriceListDTO) {
        return mapper.map(diamondPriceListDTO, Object[].class);
    }


}
