package com.letitbee.diamondvaluationsystem;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.letitbee.diamondvaluationsystem.controller.ValuationRequestController;
import com.letitbee.diamondvaluationsystem.payload.ServiceDTO;
import com.letitbee.diamondvaluationsystem.payload.ValuationRequestDTO;
import com.letitbee.diamondvaluationsystem.service.ValuationRequestService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ValuationRequestController.class)
public class ValuationRequestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ValuationRequestService valuationRequestService;

    @Test
    public void testCreateValuationRequest() throws Exception {
        ValuationRequestDTO valuationRequestDTO = new ValuationRequestDTO();
        ServiceDTO serviceDTO = new ServiceDTO();
        serviceDTO.setId(1);
        valuationRequestDTO.setService(serviceDTO);
        valuationRequestDTO.setDiamondAmount(1);
        valuationRequestDTO.setCustomerID(Long.valueOf(2));
        when(valuationRequestService.createValuationRequest(valuationRequestDTO)).thenReturn(valuationRequestDTO);

        mockMvc.perform(post("/api/v1/valuation-requests")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(valuationRequestDTO)))
                .andExpect(status().isCreated());
    }

//    @Test
//    public void testCreateValuationRequest_ServiceBlank() throws Exception {
//        ValuationRequestDTO valuationRequestDTO = new ValuationRequestDTO();
//        valuationRequestDTO.setService(null);
//        valuationRequestDTO.setDiamondAmount(1);
//        valuationRequestDTO.setCustomerID(Long.valueOf(2));
//
//        mockMvc.perform(post("/api/v1/valuation-requests")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(new ObjectMapper().writeValueAsString(valuationRequestDTO)))
//                .andExpect(status().isBadRequest());
//    }
//
//    @Test
//    public void testCreateValuationRequest_QuantityZero() throws Exception {
//        ValuationRequestDTO valuationRequestDTO = new ValuationRequestDTO();
//        ServiceDTO serviceDTO = new ServiceDTO();
//        serviceDTO.setId(1);
//        valuationRequestDTO.setService(serviceDTO);
//        valuationRequestDTO.setDiamondAmount(0);
//        valuationRequestDTO.setCustomerID(Long.valueOf(2));
//
//        mockMvc.perform(post("/api/v1/valuation-requests")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(new ObjectMapper().writeValueAsString(valuationRequestDTO)))
//                .andExpect(status().isBadRequest());
//    }

}
