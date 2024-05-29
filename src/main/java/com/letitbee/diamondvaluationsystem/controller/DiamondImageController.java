package com.letitbee.diamondvaluationsystem.controller;

import com.letitbee.diamondvaluationsystem.payload.DiamondImageDTO;
import com.letitbee.diamondvaluationsystem.service.DiamondImageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/diamond-images")
public class DiamondImageController {
    private DiamondImageService diamondImageService;

    public DiamondImageController(DiamondImageService diamondImageService) {
        this.diamondImageService = diamondImageService;
    }

    @PostMapping
    public ResponseEntity<DiamondImageDTO> createDiamondImage(@RequestBody DiamondImageDTO diamondImageDTO) {
        return ResponseEntity.ok(diamondImageService.createDiamondImage(diamondImageDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DiamondImageDTO> updateDiamondImage(@PathVariable("id") long id,
                                                              @RequestBody DiamondImageDTO diamondImageDTO) {
        return ResponseEntity.ok(diamondImageService.updateDiamondImage(id, diamondImageDTO));
    }

}
