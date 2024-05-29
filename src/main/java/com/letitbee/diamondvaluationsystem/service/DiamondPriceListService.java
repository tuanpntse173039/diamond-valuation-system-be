package com.letitbee.diamondvaluationsystem.service;

import com.letitbee.diamondvaluationsystem.enums.*;
import com.letitbee.diamondvaluationsystem.payload.DiamondPriceListDTO;
import com.letitbee.diamondvaluationsystem.payload.Response;

public interface DiamondPriceListService {

    DiamondPriceListDTO createDiamondPriceList(DiamondPriceListDTO diamondPriceListDTO);

    DiamondPriceListDTO getDiamondPriceListByField(DiamondPriceListDTO diamondPriceListDTO);

}
