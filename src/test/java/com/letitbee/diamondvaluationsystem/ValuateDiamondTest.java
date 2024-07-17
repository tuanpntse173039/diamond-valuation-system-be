package com.letitbee.diamondvaluationsystem;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.letitbee.diamondvaluationsystem.entity.DiamondValuationAssign;
import com.letitbee.diamondvaluationsystem.payload.DiamondValuationAssignDTO;
import com.letitbee.diamondvaluationsystem.repository.DiamondValuationAssignRepository;
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
@WithMockUser(authorities = "MANAGER")
public class ValuateDiamondTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private DiamondValuationAssignRepository diamondValuationAssignRepository;
    @Test
    @Transactional
    @Rollback
    public void priceMustNotBeBlank() throws Exception {

        DiamondValuationAssign diamondValuationAssign =  diamondValuationAssignRepository.findAll().get(1);
        DiamondValuationAssignDTO diamondValuationAssignDTO = mapToDTO(diamondValuationAssign);
                ;
        diamondValuationAssignDTO.setValuationPrice(0);
        diamondValuationAssignDTO.setStatus(true);
        String diamondAssignJson = objectMapper.writeValueAsString(diamondValuationAssignDTO);

        mockMvc.perform(put("/api/v1/diamond-valuation-assigns/" + diamondValuationAssignDTO.getId())
                        .contentType("application/json")
                        .content(diamondAssignJson))
                .andExpect(status().isBadRequest());
    }

    private DiamondValuationAssignDTO mapToDTO(DiamondValuationAssign diamondValuationAssign) {
        return modelMapper.map(diamondValuationAssign, DiamondValuationAssignDTO.class);
    }

    @Test
    @Rollback
    @Transactional
    public void commentMustNotBeBlank() throws Exception {

        DiamondValuationAssign diamondValuationAssign =  diamondValuationAssignRepository.findAll().get(1);
        DiamondValuationAssignDTO diamondValuationAssignDTO = mapToDTO(diamondValuationAssign);
        diamondValuationAssignDTO.setComment("");
        String diamondAssignJson = objectMapper.writeValueAsString(diamondValuationAssignDTO);

        mockMvc.perform(put("/api/v1/diamond-valuation-assigns/" + diamondValuationAssignDTO.getId())
                        .contentType("application/json")
                        .content(diamondAssignJson))
                .andExpect(status().isBadRequest());
    }
    @Test
    @Rollback
    @Transactional
    public void briefMustNotBeBlank() throws Exception {

        DiamondValuationAssign diamondValuationAssign =  diamondValuationAssignRepository.findAll().get(1);
        DiamondValuationAssignDTO diamondValuationAssignDTO = mapToDTO(diamondValuationAssign);
        diamondValuationAssignDTO.setCommentDetail("");
        String diamondAssignJson = objectMapper.writeValueAsString(diamondValuationAssignDTO);

        mockMvc.perform(put("/api/v1/diamond-valuation-assigns/" + diamondValuationAssignDTO.getId())
                        .contentType("application/json")
                        .content(diamondAssignJson))
                .andExpect(status().isBadRequest());
    }


}
