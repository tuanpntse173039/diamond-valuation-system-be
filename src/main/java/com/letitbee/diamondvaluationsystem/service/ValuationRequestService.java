package com.letitbee.diamondvaluationsystem.service;

import com.letitbee.diamondvaluationsystem.payload.Response;
import com.letitbee.diamondvaluationsystem.payload.ValuationRequestDTO;

public interface ValuationRequestService {

    Response<ValuationRequestDTO> getAllValuationRequests(int pageNo, int pageSize, String sortBy, String sortDir);

    ValuationRequestDTO getValuationRequestById(Long id);

    ValuationRequestDTO createValuationRequest(ValuationRequestDTO valuationRequestDto);

    ValuationRequestDTO updateValuationRequest(long id, ValuationRequestDTO valuationRequestDTO);
    ValuationRequestDTO deleteValuationRequestById(Long id);

}