package com.letitbee.diamondvaluationsystem;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(authorities = "CUSTOMER")
public class DiamondValuationNote {

    @Autowired
    private MockMvc mockMvc;


    @Test
    public void testGetAllDiamondValuationNoteByCertificateId() throws Exception {
        String certificateId = "0367304355";


        mockMvc.perform(get("/api/v1/diamond-valuation-notes/search")
                .param("certificateId", certificateId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testSearchCertificateIdNotFound() throws Exception {
        String certificateId = "98888888";

        mockMvc.perform(get("/api/v1/diamond-valuation-notes/search")
                .param("certificateId", certificateId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }


}
