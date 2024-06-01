package com.letitbee.diamondvaluationsystem.controller;

import com.letitbee.diamondvaluationsystem.payload.DiamondValuationAssignDTO;
import com.letitbee.diamondvaluationsystem.service.DiamondValuationAssignService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/diamond-valuation-assigns")
public class DiamondValuationAssignController {
    private DiamondValuationAssignService diamondValuationAssignService;

    public DiamondValuationAssignController(DiamondValuationAssignService diamondValuationAssignService) {
        this.diamondValuationAssignService = diamondValuationAssignService;
    }

    @PostMapping
    public ResponseEntity<DiamondValuationAssignDTO> createDiamondValuationAssign
            (@RequestBody DiamondValuationAssignDTO diamondValuationAssignDTO) {
        return new ResponseEntity<>(diamondValuationAssignService.createDiamondValuationAssign(diamondValuationAssignDTO),
                HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DiamondValuationAssignDTO> updateDiamondValuationAssign
            (@PathVariable("id") long id,
             @RequestBody DiamondValuationAssignDTO diamondValuationAssignDTO) {
        return ResponseEntity.ok(diamondValuationAssignService.updateDiamondValuationAssign(id, diamondValuationAssignDTO));
    }


}
