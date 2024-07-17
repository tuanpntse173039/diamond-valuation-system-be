package com.letitbee.diamondvaluationsystem;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.letitbee.diamondvaluationsystem.entity.DiamondValuationAssign;
import com.letitbee.diamondvaluationsystem.entity.ValuationRequest;
import com.letitbee.diamondvaluationsystem.enums.RequestStatus;
import com.letitbee.diamondvaluationsystem.payload.DiamondValuationAssignDTO;
import com.letitbee.diamondvaluationsystem.payload.ValuationRequestDTO;
import com.letitbee.diamondvaluationsystem.repository.DiamondValuationAssignRepository;
import com.letitbee.diamondvaluationsystem.repository.ValuationRequestRepository;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(authorities = "CUSTOMER")
public class CancellationTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ValuationRequestRepository valuationRequestRepository;
    @Test
    @Transactional
    @Rollback
    public void cancelReasonMustNotBlank() throws Exception {
        ValuationRequest valuationRequest = valuationRequestRepository.findById(201L).orElse(null);
        ValuationRequestDTO valuationRequestDTO = mapToDTO(valuationRequest);
        valuationRequestDTO.setStatus(RequestStatus.CANCEL);
        valuationRequestDTO.setCancelReason("");
        String valuationRequestJson = objectMapper.writeValueAsString(valuationRequestDTO);

        mockMvc.perform(put("/api/v1/valuation-requests/" + valuationRequestDTO.getId())
                        .contentType("application/json")
                        .content(valuationRequestJson))
                .andExpect(status().isBadRequest());

    }

    private ValuationRequestDTO mapToDTO(ValuationRequest valuationRequest) {
        return modelMapper.map(valuationRequest, ValuationRequestDTO.class);
    }

}
