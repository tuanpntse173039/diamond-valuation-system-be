package com.letitbee.diamondvaluationsystem;

import com.letitbee.diamondvaluationsystem.controller.DiamondValuationNoteController;
import com.letitbee.diamondvaluationsystem.payload.DiamondValuationNoteDTO;
import com.letitbee.diamondvaluationsystem.service.DiamondValuationNoteService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = DiamondValuationNoteController.class)
public class DiamondValuationNoteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DiamondValuationNoteService diamondValuationNoteService;

    @Test
    public void testGetAllDiamondValuationNoteByCertificateId() throws Exception {
        String certificateId = "0367304355";

        when(diamondValuationNoteService.getAllDiamondValuationNoteByCertificateId(certificateId)).thenReturn(new DiamondValuationNoteDTO());

        mockMvc.perform(get("/api/v1/diamond-valuation-notes/search")
                .param("certificateId", certificateId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetAllDiamondValuationNoteByCertificateId_NotNumeric() throws Exception {
        String certificateId = "notNumeric";

        mockMvc.perform(get("/api/v1/diamond-valuation-notes/search")
                .param("certificateId", certificateId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
//
//    @Test
//    public void testGetAllDiamondValuationNoteByCertificateId_Not10Digits() throws Exception {
//        String certificateId = "12345";
//
//        mockMvc.perform(get("/api/v1/diamond-valuation-notes/search")
//                .param("certificateId", certificateId)
//                .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isBadRequest());
//    }
}
