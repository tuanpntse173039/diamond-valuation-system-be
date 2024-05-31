package com.letitbee.diamondvaluationsystem.service.impl;

import com.letitbee.diamondvaluationsystem.entity.DiamondMarket;
import com.letitbee.diamondvaluationsystem.exception.APIException;
import com.letitbee.diamondvaluationsystem.payload.DiamondMarketDTO;
import com.letitbee.diamondvaluationsystem.payload.DiamondPriceListDTO;
import com.letitbee.diamondvaluationsystem.repository.DiamondMarketRepository;
import com.letitbee.diamondvaluationsystem.service.DiamondMarketService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DiamondMarketServiceImpl implements DiamondMarketService {
    private DiamondMarketRepository diamondMarketRepository;
    private ModelMapper mapper;

    public DiamondMarketServiceImpl(DiamondMarketRepository diamondMarketRepository, ModelMapper mapper) {
        this.diamondMarketRepository = diamondMarketRepository;
        this.mapper = mapper;
    }
    @Override
    public DiamondMarketDTO createDiamondMarket(DiamondMarketDTO diamondMarketDTO) {
        DiamondMarket diamondMarket = mapToEntity(diamondMarketDTO);
        return mapToDTO(diamondMarketRepository.save(diamondMarket));
    }

    @Override
    public List<DiamondMarketDTO> getAllDiamondMarket(DiamondPriceListDTO diamondPriceListDTO) {
        List<DiamondMarket> diamondMarket = diamondMarketRepository.findSelectedFieldsByDiamondProperties(
                diamondPriceListDTO.getDiamondOrigin(),
                diamondPriceListDTO.getCaratWeight(),
                diamondPriceListDTO.getColor(),
                diamondPriceListDTO.getClarity());
         if (diamondMarket != null && !diamondMarket.isEmpty()) {
            return diamondMarket.stream().map(DiamondMarket -> mapToDTO(DiamondMarket)).toList();
         }
        else throw new APIException(HttpStatus.NOT_FOUND,"No diamond market data found");
    }

    private DiamondMarketDTO mapToDTO(DiamondMarket diamondMarket) {
        return mapper.map(diamondMarket, DiamondMarketDTO.class);
    }

    private DiamondMarket mapToEntity(DiamondMarketDTO diamondMarketDTO) {
        return mapper.map(diamondMarketDTO, DiamondMarket.class);
    }
}
