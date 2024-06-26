package com.letitbee.diamondvaluationsystem.service;

import com.letitbee.diamondvaluationsystem.enums.RecordType;
import com.letitbee.diamondvaluationsystem.payload.RecordDTO;

import java.util.List;

public interface RecordService {
    RecordDTO createRecord(RecordDTO recordDTO);
    RecordDTO updateRecord(Long id,RecordDTO recordDTO);
    RecordDTO getRecord(Long id);
    void deleteRecord(Long id);
    List<RecordDTO> getRecordsByRequestId(Long requestId);
    RecordDTO getRecordByRequestIdAndType(Long requestId, RecordType type);
}
