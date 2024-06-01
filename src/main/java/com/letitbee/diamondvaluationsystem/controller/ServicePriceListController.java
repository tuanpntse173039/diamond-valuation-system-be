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

    @GetMapping
    public ResponseEntity<List<ServicePriceListDTO>> getAllServicePriceList() {
        return new ResponseEntity<>(servicePriceListService.getAllServicePriceList(), HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<ServicePriceListDTO> getServicePriceListById(@PathVariable(name = "id") long id) {
        return new ResponseEntity<>(servicePriceListService.getServicePriceListById(id), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<ServicePriceListDTO> createServicePriceList(@RequestBody ServicePriceListDTO servicePriceListDto) {
        return new ResponseEntity<>(servicePriceListService.createServicePriceList(servicePriceListDto), HttpStatus.CREATED);
    }

}