package com.letitbee.diamondvaluationsystem.controller;

import com.letitbee.diamondvaluationsystem.entity.ValuationRequest;
import com.letitbee.diamondvaluationsystem.payload.Response;
import com.letitbee.diamondvaluationsystem.payload.ValuationRequestDTO;
import com.letitbee.diamondvaluationsystem.service.ValuationRequestService;
import com.letitbee.diamondvaluationsystem.utils.AppConstraint;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

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
                           @RequestParam(name = "sortDir", defaultValue = AppConstraint.SORT_DIR, required = false) String sortDir,
                           @RequestParam(name = "startDate", defaultValue = AppConstraint.START_DATE, required = false) String startDate,
                           @RequestParam(name = "endDate", defaultValue = AppConstraint.END_DATE, required = false) String endDate
                           ) {
        Date startDateParse = new Date(startDate);
        Date endDateParse = new Date(endDate);
        return new ResponseEntity<>(valuationRequestService.getAllValuationRequests(pageNo, pageSize, sortBy, sortDir, startDateParse, endDateParse), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ValuationRequestDTO> getValuationRequest(@PathVariable("id") long id) {
        return ResponseEntity.ok(valuationRequestService.getValuationRequestById(id));
    }

    @PostMapping
    public ResponseEntity<ValuationRequestDTO> createValuationRequest(
            @RequestBody @Valid ValuationRequestDTO valuationRequestDT) {
        return new ResponseEntity<>(valuationRequestService.createValuationRequest(valuationRequestDT), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ValuationRequestDTO> updateValuationRequest(
            @PathVariable("id") long id,
            @RequestBody @Valid ValuationRequestDTO valuationRequestDT) {
        return ResponseEntity.ok(valuationRequestService.updateValuationRequest(id, valuationRequestDT));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ValuationRequestDTO> deleteValuationRequest(
            @PathVariable("id") long id) {
        return ResponseEntity.ok(valuationRequestService.deleteValuationRequestById(id));
    }

}
