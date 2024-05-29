package com.letitbee.diamondvaluationsystem.controller;

import com.letitbee.diamondvaluationsystem.entity.DiamondValuationNote;
import com.letitbee.diamondvaluationsystem.payload.DiamondValuationNoteDTO;
import com.letitbee.diamondvaluationsystem.payload.Response;
import com.letitbee.diamondvaluationsystem.service.DiamondValuationNoteService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/diamond-valuation-notes")
public class DiamondValuationNoteController {
    private DiamondValuationNoteService valuationNoteService;

    public DiamondValuationNoteController(DiamondValuationNoteService valuationNoteService) {
        this.valuationNoteService = valuationNoteService;
    }

    @PutMapping("{id}")
    public ResponseEntity<DiamondValuationNoteDTO>
    updateDiamondValuationNote(@PathVariable("id") long id,
                               @RequestBody DiamondValuationNoteDTO valuationNoteDTO
    ) {
        return ResponseEntity.ok(valuationNoteService.updateDiamondValuationNote(id, valuationNoteDTO));
    }
}
