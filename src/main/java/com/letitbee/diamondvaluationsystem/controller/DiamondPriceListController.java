package com.letitbee.diamondvaluationsystem.controller;

import com.letitbee.diamondvaluationsystem.enums.*;
import com.letitbee.diamondvaluationsystem.payload.DiamondPriceListDTO;
import com.letitbee.diamondvaluationsystem.payload.Response;
import com.letitbee.diamondvaluationsystem.service.DiamondPriceListService;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/diamond-price-lists")
public class DiamondPriceListController {

    private DiamondPriceListService diamondPriceListService;

    public DiamondPriceListController(DiamondPriceListService diamondPriceListService) {
        this.diamondPriceListService = diamondPriceListService;
    }



    @GetMapping("/search")
    public ResponseEntity<DiamondPriceListDTO> getDiamondPriceListByField(
            @RequestParam(value = "diamondOrigin") DiamondOrigin diamondOrigin,
            @RequestParam(value = "caratWeight") float caratWeight,
            @RequestParam(value = "color") Color color,
            @RequestParam(value = "clarity") Clarity clarity,
            @RequestParam(value = "cut") Cut cut,
            @RequestParam(value = "polish") Polish polish,
            @RequestParam(value = "symmetry") Symmetry symmetry,
            @RequestParam(value = "shape") Shape shape,
            @RequestParam(value = "fluorescence") Fluorescence fluorescence
    ) {
        DiamondPriceListDTO response = diamondPriceListService.getDiamondPriceListByField(
                diamondOrigin,
                caratWeight,
                color,
                clarity,
                cut,
                polish,
                symmetry,
                shape,
                fluorescence);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<DiamondPriceListDTO> createDiamondPriceList(@RequestBody DiamondPriceListDTO diamondPriceListDTO) {
        DiamondPriceListDTO response = diamondPriceListService.createDiamondPriceList(diamondPriceListDTO);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
