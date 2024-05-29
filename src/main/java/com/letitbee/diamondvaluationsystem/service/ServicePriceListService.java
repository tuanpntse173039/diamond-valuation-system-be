package com.letitbee.diamondvaluationsystem.service;

import com.letitbee.diamondvaluationsystem.payload.ServicePriceListDTO;

import java.util.List;

public interface ServicePriceListService {

    List<ServicePriceListDTO> getAllServicePriceList(long serviceId);

    ServicePriceListDTO getServicePriceListById(long serviceId, long id);

    ServicePriceListDTO createServicePriceList(long serviceId,ServicePriceListDTO servicePriceListDto);
}
