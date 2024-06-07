package com.letitbee.diamondvaluationsystem.service.impl;

import com.letitbee.diamondvaluationsystem.entity.DiamondMarket;
import com.letitbee.diamondvaluationsystem.entity.DiamondPriceList;
import com.letitbee.diamondvaluationsystem.enums.*;
import com.letitbee.diamondvaluationsystem.exception.APIException;
import com.letitbee.diamondvaluationsystem.payload.DiamondPriceListDTO;
import com.letitbee.diamondvaluationsystem.repository.DiamondMarketRepository;
import com.letitbee.diamondvaluationsystem.repository.DiamondPriceListRepository;
import com.letitbee.diamondvaluationsystem.service.DiamondPriceListService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class DiamondPriceListServiceImpl implements DiamondPriceListService {

    private DiamondPriceListRepository diamondPriceListRepository;
    private DiamondMarketRepository diamondMarketRepository;

    private ModelMapper mapper;

    public DiamondPriceListServiceImpl(DiamondPriceListRepository diamondPriceListRepository,
                                       ModelMapper mapper,
                                       DiamondMarketRepository diamondMarketRepository) {
        this.diamondPriceListRepository = diamondPriceListRepository;
        this.mapper = mapper;
        this.diamondMarketRepository = diamondMarketRepository;
    }


    @Override
    public DiamondPriceListDTO createDiamondPriceList(DiamondPriceListDTO diamondPriceListDTO) {
        diamondPriceListDTO.setCreationDate(new Date());
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
        List<DiamondMarket> diamondMarket = diamondMarketRepository.findSelectedFieldsByDiamondProperties(
                diamondOrigin,
                caratWeight,
                color,
                clarity,
                polish,
                symmetry,
                shape,
                fluorescence);
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
            diamondPriceList.setMinPrice(diamondMarket.stream().findFirst().get().getPrice());
            diamondPriceList.setMaxPrice(diamondMarket.get(diamondMarket.size()-1).getPrice());
            double fairPrice = 0;
            for (DiamondMarket diamondMarkets : diamondMarket) {
                fairPrice += diamondMarkets.getPrice();
            }
            fairPrice = fairPrice / diamondMarket.size();
            diamondPriceList.setFairPrice(fairPrice);
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
