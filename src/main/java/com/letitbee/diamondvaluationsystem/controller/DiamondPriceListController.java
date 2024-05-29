package com.letitbee.diamondvaluationsystem.controller;

import com.letitbee.diamondvaluationsystem.payload.DiamondPriceListDTO;
import com.letitbee.diamondvaluationsystem.payload.Response;
import com.letitbee.diamondvaluationsystem.service.DiamondPriceListService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/diamond-price-list")
public class DiamondPriceListController {

    private DiamondPriceListService diamondPriceListService;

    public DiamondPriceListController(DiamondPriceListService diamondPriceListService) {
        this.diamondPriceListService = diamondPriceListService;
    }

    @GetMapping
    public ResponseEntity<DiamondPriceListDTO> getDiamondPriceListByField(@RequestBody DiamondPriceListDTO diamondPriceListDTO) {
        DiamondPriceListDTO response = diamondPriceListService.getDiamondPriceListByField(diamondPriceListDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
