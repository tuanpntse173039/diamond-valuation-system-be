package com.letitbee.diamondvaluationsystem.controller;

import com.letitbee.diamondvaluationsystem.entity.Record;
import com.letitbee.diamondvaluationsystem.enums.RecordType;
import com.letitbee.diamondvaluationsystem.payload.RecordDTO;
import com.letitbee.diamondvaluationsystem.service.RecordService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/records")
public class RecordController {

    private RecordService recordService;

    public RecordController(RecordService recordService) {
        this.recordService = recordService;
    }

    @GetMapping("/by-request-id/{requestId}")
    public ResponseEntity<List<RecordDTO>> getRecordsByRequestId(@PathVariable(name = "requestId") Long requestId) {
        return ResponseEntity.ok(recordService.getRecordsByRequestId(requestId));
    }

    @GetMapping("/by-request-id")
    public ResponseEntity<RecordDTO> getRecordByRequestIdAndType(@RequestParam(name = "requestId", required = false) Long requestId,
                                                                 @RequestParam(name = "type", required = false) RecordType type) {
        return ResponseEntity.ok(recordService.getRecordByRequestIdAndType(requestId, type));
    }

    @PostMapping
    public ResponseEntity<RecordDTO> createRecord(@RequestBody RecordDTO recordDTO) {
        return new ResponseEntity<>(recordService.createRecord(recordDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RecordDTO> updateRecord(@RequestBody RecordDTO recordDTO, @PathVariable(name  = "id") Long id) {
        return ResponseEntity.ok(recordService.updateRecord(id,recordDTO));
    }
}
