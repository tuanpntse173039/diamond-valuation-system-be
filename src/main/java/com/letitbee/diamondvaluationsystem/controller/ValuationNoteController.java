package com.letitbee.diamondvaluationsystem.controller;

import com.letitbee.diamondvaluationsystem.payload.Response;
import com.letitbee.diamondvaluationsystem.payload.ValuationRequestDetailDTO;
import com.letitbee.diamondvaluationsystem.service.ValuationRequestDetailService;
import com.letitbee.diamondvaluationsystem.utils.AppConstraint;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("api/v1/valuation-notes")
public class ValuationNoteController {
    private ValuationRequestDetailService valuationRequestDetailService;

    public ValuationNoteController(ValuationRequestDetailService valuationRequestDetailService) {
        this.valuationRequestDetailService = valuationRequestDetailService;
    }

    @GetMapping
    public ResponseEntity<Response<ValuationRequestDetailDTO>> getAllValuationNote(@RequestParam(name = "pageNo", defaultValue = AppConstraint.PAGE_NO, required = false) int pageNo,
                                                                                   @RequestParam(name = "pageSize", defaultValue = AppConstraint.PAGE_SIZE, required = false) int pageSize,
                                                                                   @RequestParam(name = "sortBy", defaultValue = AppConstraint.SORT_BY, required = false) String sortBy,
                                                                                   @RequestParam(name = "sortDir", defaultValue = AppConstraint.SORT_DIR, required = false) String sortDir) {
        return new ResponseEntity<>(valuationRequestDetailService.getAllValuationNotes(pageNo, pageSize, sortBy, sortDir), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ValuationRequestDetailDTO> getValuationNoteById(@PathVariable long id) {
        return ResponseEntity.ok(valuationRequestDetailService.getValuationNoteById(id));
    }
}
