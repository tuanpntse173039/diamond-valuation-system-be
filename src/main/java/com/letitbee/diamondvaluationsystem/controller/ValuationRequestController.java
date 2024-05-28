package com.letitbee.diamondvaluationsystem.controller;

import com.letitbee.diamondvaluationsystem.entity.ValuationRequest;
import com.letitbee.diamondvaluationsystem.payload.Response;
import com.letitbee.diamondvaluationsystem.payload.ValuationRequestDTO;
import com.letitbee.diamondvaluationsystem.service.ValuationRequestService;
import com.letitbee.diamondvaluationsystem.utils.AppConstraint;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("api/v1/valuation-requests")
public class ValuationRequestController {
    private ValuationRequestService valuationRequestService;

    public ValuationRequestController(ValuationRequestService valuationRequestService) {
        this.valuationRequestService = valuationRequestService;
    }

    @GetMapping
    public ResponseEntity<Response<ValuationRequestDTO>>
    getAllValuationRequest(@RequestParam(name = "pageNo", defaultValue = AppConstraint.PAGE_NO, required = false) int pageNo,
                           @RequestParam(name = "pageSize", defaultValue = AppConstraint.PAGE_SIZE, required = false) int pageSize,
                           @RequestParam(name = "sortBy", defaultValue = AppConstraint.SORT_BY, required = false) String sortBy,
                           @RequestParam(name = "sortDir", defaultValue = AppConstraint.SORT_DIR, required = false) String sortDir) {
        return new ResponseEntity<>(valuationRequestService.getAllValuationRequests(pageNo, pageSize, sortBy, sortDir), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ValuationRequestDTO> getValuationRequest(@PathVariable("id") long id) {
        return ResponseEntity.ok(valuationRequestService.getValuationRequestById(id));
    }

    @PostMapping
    public ResponseEntity<ValuationRequestDTO> createValuationRequest(
            @RequestBody ValuationRequestDTO valuationRequestDT) {
        return ResponseEntity.ok(valuationRequestService.createValuationRequest(valuationRequestDT));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ValuationRequestDTO> updateValuationRequest(
            @PathVariable("id") long id,
            @RequestBody ValuationRequestDTO valuationRequestDT) {
        return ResponseEntity.ok(valuationRequestService.updateValuationRequest(id, valuationRequestDT));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ValuationRequestDTO> deleteValuationRequest(
            @PathVariable("id") long id) {
        return ResponseEntity.ok(valuationRequestService.deleteValuationRequestById(id));
    }

}