package com.letitbee.diamondvaluationsystem.service;

import com.letitbee.diamondvaluationsystem.enums.*;
import com.letitbee.diamondvaluationsystem.payload.DiamondMarketDTO;
import com.letitbee.diamondvaluationsystem.payload.DiamondPriceListDTO;
import com.letitbee.diamondvaluationsystem.payload.Response;

import java.util.List;

public interface DiamondMarketService {
    DiamondMarketDTO createDiamondMarket(DiamondMarketDTO diamondMarketDTO);

    List<DiamondMarketDTO> searchDiamonds(
            DiamondOrigin diamondOrigin,
            float caratWeight,
            Color color,
            Clarity clarity,
            Cut cut,
            Polish polish,
            Symmetry symmetry,
            Shape shape,
            Fluorescence fluorescence
    );


    DiamondPriceListDTO getDiamondPriceList(
            DiamondOrigin diamondOrigin,
            float caratWeight,
            Color color,
            Clarity clarity,
            Cut cut,
            Polish polish,
            Symmetry symmetry,
            Shape shape,
            Fluorescence fluorescence
    );

    void deleteDiamondMarket(long id);

    Response<DiamondMarketDTO> getAllDiamondMarket(int pageNo, int pageSize, String sortBy, String sortDir);


    void crawlDiamondMarket(String name);

}
