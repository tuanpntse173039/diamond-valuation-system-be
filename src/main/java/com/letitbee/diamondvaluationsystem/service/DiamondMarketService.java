package com.letitbee.diamondvaluationsystem.service;

import com.letitbee.diamondvaluationsystem.payload.DiamondMarketDTO;
import com.letitbee.diamondvaluationsystem.payload.DiamondPriceListDTO;

import java.util.List;

public interface DiamondMarketService {
    DiamondMarketDTO createDiamondMarket(DiamondMarketDTO diamondMarketDTO);

    List<DiamondMarketDTO> getAllDiamondMarket(DiamondPriceListDTO diamondPriceListDTO);
}
