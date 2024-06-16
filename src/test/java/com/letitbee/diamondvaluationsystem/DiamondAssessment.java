package com.letitbee.diamondvaluationsystem;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.letitbee.diamondvaluationsystem.enums.*;
import com.letitbee.diamondvaluationsystem.payload.AccountDTO;
import com.letitbee.diamondvaluationsystem.payload.DiamondValuationNoteDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
public class DiamondAssessment {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    @Transactional
    @Rollback
    public void testShapeBlank() throws Exception {
        DiamondValuationNoteDTO diamondValuationNoteDTO = new DiamondValuationNoteDTO();
        diamondValuationNoteDTO.setShape(null);
        diamondValuationNoteDTO.setCaratWeight(1.0F);
        diamondValuationNoteDTO.setColor(Color.valueOf("D"));
        diamondValuationNoteDTO.setClarity(Clarity.valueOf("IF"));
        diamondValuationNoteDTO.setCut(Cut.valueOf("EXCELLENT"));
        diamondValuationNoteDTO.setPolish(Polish.valueOf("EXCELLENT"));
        diamondValuationNoteDTO.setSymmetry(Symmetry.valueOf("EXCELLENT"));
        diamondValuationNoteDTO.setFluorescence(Fluorescence.valueOf("NONE"));

        String accountJson = objectMapper.writeValueAsString(diamondValuationNoteDTO);

        mockMvc.perform(put("/api/v1/diamond-valuation-notes/130")
                        .contentType("application/json")
                        .content(accountJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Transactional
    @Rollback
    public void testSCaratWeightBlank() throws Exception {
        DiamondValuationNoteDTO diamondValuationNoteDTO = new DiamondValuationNoteDTO();
        diamondValuationNoteDTO.setShape(Shape.valueOf("ROUND"));
        diamondValuationNoteDTO.setCaratWeight(0);
        diamondValuationNoteDTO.setColor(Color.valueOf("D"));
        diamondValuationNoteDTO.setClarity(Clarity.valueOf("IF"));
        diamondValuationNoteDTO.setCut(Cut.valueOf("EXCELLENT"));
        diamondValuationNoteDTO.setPolish(Polish.valueOf("EXCELLENT"));
        diamondValuationNoteDTO.setSymmetry(Symmetry.valueOf("EXCELLENT"));
        diamondValuationNoteDTO.setFluorescence(Fluorescence.valueOf("NONE"));

        String accountJson = objectMapper.writeValueAsString(diamondValuationNoteDTO);

        mockMvc.perform(put("/api/v1/diamond-valuation-notes/130")
                        .contentType("application/json")
                        .content(accountJson))
                .andExpect(status().isBadRequest());
    }


}
