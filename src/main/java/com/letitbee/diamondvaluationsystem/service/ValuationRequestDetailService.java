package com.letitbee.diamondvaluationsystem.service;

import com.letitbee.diamondvaluationsystem.payload.Response;
import com.letitbee.diamondvaluationsystem.payload.ValuationRequestDetailDTO;

public interface ValuationRequestDetailService {
    Response<ValuationRequestDetailDTO> getAllValuationNotes(int pageNo, int pageSize, String sortBy, String sortDir);

    ValuationRequestDetailDTO getValuationNoteById(Long id);

    ValuationRequestDetailDTO createValuationNote(ValuationRequestDetailDTO valuationRequestDetailDto);

    void deleteValuationNoteById(Long id);
}
