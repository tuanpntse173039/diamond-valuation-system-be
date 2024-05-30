package com.letitbee.diamondvaluationsystem.controller;

import com.letitbee.diamondvaluationsystem.payload.ServicePriceListDTO;
import com.letitbee.diamondvaluationsystem.service.ServicePriceListService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/service-price-lists")
public class ServicePriceListController
{
    private ServicePriceListService servicePriceListService;

    public ServicePriceListController(ServicePriceListService servicePriceListService) {
        this.servicePriceListService = servicePriceListService;
    }

    public ResponseEntity<List<ServicePriceListDTO>> getAllServicePriceList(@PathVariable(name = "serviceId") long serviceId) {
        return new ResponseEntity<>(servicePriceListService.getAllServicePriceList(serviceId), HttpStatus.OK);
    }

    @GetMapping("services/{serviceId}/service-price-list/{id}")
    public ResponseEntity<ServicePriceListDTO> getServicePriceListById(@PathVariable(name = "serviceId") long serviceId,
                                                                       @PathVariable(name = "id") long id) {
        return new ResponseEntity<>(servicePriceListService.getServicePriceListById(serviceId, id), HttpStatus.OK);
    }

    @PostMapping("services/{serviceId}/service-price-list")
    public ResponseEntity<ServicePriceListDTO> createServicePriceList(@PathVariable(name = "serviceId") long serviceId,
                                                                     @RequestBody ServicePriceListDTO servicePriceListDto) {
        return new ResponseEntity<>(servicePriceListService.createServicePriceList(serviceId, servicePriceListDto), HttpStatus.CREATED);
    }

}
