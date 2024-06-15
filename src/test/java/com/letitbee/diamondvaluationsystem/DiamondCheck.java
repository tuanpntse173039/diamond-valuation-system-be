package com.letitbee.diamondvaluationsystem;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.letitbee.diamondvaluationsystem.controller.AccountController;
import com.letitbee.diamondvaluationsystem.controller.DiamondValuationNoteController;
import com.letitbee.diamondvaluationsystem.enums.Role;
import com.letitbee.diamondvaluationsystem.payload.AccountDTO;
import com.letitbee.diamondvaluationsystem.service.AccountService;
import com.letitbee.diamondvaluationsystem.service.DiamondValuationNoteService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = DiamondValuationNoteController.class)
public class DiamondCheck {
  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @MockBean
  private DiamondValuationNoteService valuationNoteService;

//  @Test
//  public void testDC_CertificateId_MustNotBeBlank() throws Exception {
//    String certificateId = "";
//
//    String certificateJson = objectMapper.writeValueAsString(certificateId);
//
//    mockMvc.perform(get("/api/v1/diamond-valuation-notes/search")
//                    .contentType("application/json")
//                    .content(certificateJson))
//            .andExpect(status().isBadRequest());
//  }
}
