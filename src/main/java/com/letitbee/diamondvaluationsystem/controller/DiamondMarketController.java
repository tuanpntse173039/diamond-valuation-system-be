package com.letitbee.diamondvaluationsystem.controller;

import com.letitbee.diamondvaluationsystem.entity.DiamondMarket;
import com.letitbee.diamondvaluationsystem.enums.*;
import com.letitbee.diamondvaluationsystem.payload.DiamondMarketDTO;
import com.letitbee.diamondvaluationsystem.payload.DiamondPriceListDTO;
import com.letitbee.diamondvaluationsystem.payload.Response;
import com.letitbee.diamondvaluationsystem.service.DiamondMarketService;
import com.letitbee.diamondvaluationsystem.utils.AppConstraint;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @GetMapping("/search")
    public ResponseEntity<List<DiamondMarketDTO>> getAllDiamondMarket(
            @RequestParam(value = "diamondOrigin") DiamondOrigin diamondOrigin,
            @RequestParam(value = "caratWeight") float caratWeight,
            @RequestParam(value = "color") Color color,
            @RequestParam(value = "clarity") Clarity clarity,
            @RequestParam(value = "cut") Cut cut,
            @RequestParam(value = "polish") Polish polish,
            @RequestParam(value = "symmetry") Symmetry symmetry,
            @RequestParam(value = "shape") Shape shape,
            @RequestParam(value = "fluorescence") Fluorescence fluorescence
    ){
        return new ResponseEntity<>(diamondMarketService.searchDiamonds(
                diamondOrigin,
                caratWeight,
                color,
                clarity,
                cut,
                polish,
                symmetry,
                shape,
                fluorescence), HttpStatus.OK);
    }


    @GetMapping("/price-list")
    public ResponseEntity<DiamondPriceListDTO> getDiamondPriceList(
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
        return new ResponseEntity<>(diamondMarketService.getDiamondPriceList(
                diamondOrigin,
                caratWeight,
                color,
                clarity,
                cut,
                polish,
                symmetry,
                shape,
                fluorescence), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Response<DiamondMarketDTO>> getAllDiamondMarket(
            @RequestParam(name = "pageNo", defaultValue = AppConstraint.PAGE_NO, required = false) int pageNo,
            @RequestParam(name = "pageSize", defaultValue = AppConstraint.PAGE_SIZE, required = false) int pageSize,
            @RequestParam(name = "sortBy", defaultValue = AppConstraint.SORT_BY, required = false) String sortBy,
            @RequestParam(name = "sortDir", defaultValue = AppConstraint.SORT_DIR, required = false) String sortDir
            ) {
        return new ResponseEntity<>(diamondMarketService.getAllDiamondMarket(pageNo, pageSize, sortBy, sortDir), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('MANAGER')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDiamondMarket(@PathVariable(name = "id") long id) {
        diamondMarketService.deleteDiamondMarket(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/crawl/supplier/{id}")
    public ResponseEntity<String> crawl(@PathVariable(name = "id") long id) {
        diamondMarketService.crawlDiamondMarket(id);
        return new ResponseEntity<>("Craw success", HttpStatus.OK);
    }
}
