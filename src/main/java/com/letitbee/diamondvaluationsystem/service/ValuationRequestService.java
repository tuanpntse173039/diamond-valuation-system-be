package com.letitbee.diamondvaluationsystem.service;
import com.letitbee.diamondvaluationsystem.payload.ValuationRequestDTO;
public interface ValuationRequestService {

    ValuationRequestDTO getAllValuationRequests(int pageNo, int pageSize, String sortBy, String sortDir);

    ValuationRequestDTO getValuationRequestById(Long id);

    ValuationRequestDTO createValuationRequest(ValuationRequestDTO valuationRequestDto);

    void deleteValuationRequestById(Long id);

}
