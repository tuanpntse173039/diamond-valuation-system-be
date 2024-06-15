package com.letitbee.diamondvaluationsystem;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.letitbee.diamondvaluationsystem.controller.AccountController;
import com.letitbee.diamondvaluationsystem.payload.AccountDTO;
import com.letitbee.diamondvaluationsystem.service.AccountService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = AccountController.class)
public class SignIn {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private AccountService accountService;

  @Test
  public void testLogin() throws Exception {
    AccountDTO accountDTO = new AccountDTO();
    accountDTO.setUsername("testUser");
    accountDTO.setPassword("testPassword");

    when(accountService.login(accountDTO)).thenReturn("testToken");

    mockMvc.perform(post("/api/v1/accounts/login")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(new ObjectMapper().writeValueAsString(accountDTO)))
            .andExpect(status().isOk());
  }
}