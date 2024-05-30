package com.letitbee.diamondvaluationsystem.controller;

import com.letitbee.diamondvaluationsystem.entity.DiamondMarket;
import com.letitbee.diamondvaluationsystem.payload.DiamondMarketDTO;
import com.letitbee.diamondvaluationsystem.payload.DiamondPriceListDTO;
import com.letitbee.diamondvaluationsystem.service.DiamondMarketService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/diamond-market")
public class DiamondMarketController {
    private DiamondMarketService diamondMarketService;

    public DiamondMarketController(DiamondMarketService diamondMarketService) {
        this.diamondMarketService = diamondMarketService;
    }

    @PostMapping
    public ResponseEntity<DiamondMarketDTO> createDiamondMarket(@RequestBody DiamondMarketDTO diamondMarketDTO) {
        return new ResponseEntity<>(diamondMarketService.createDiamondMarket(diamondMarketDTO), HttpStatus.CREATED);
    }

    @GetMapping("/field")
    public ResponseEntity<List<DiamondMarketDTO>> getAllDiamondMarket(@RequestBody DiamondPriceListDTO diamondPriceListDTO){
        return new ResponseEntity<>(diamondMarketService.getAllDiamondMarket(diamondPriceListDTO), HttpStatus.OK);
    }
}
