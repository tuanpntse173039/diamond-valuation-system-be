package com.letitbee.diamondvaluationsystem.testcontroller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.letitbee.diamondvaluationsystem.enums.Role;
import com.letitbee.diamondvaluationsystem.payload.AccountDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@WithMockUser
@SpringBootTest
@AutoConfigureMockMvc
public class TestCreateAccountController {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;
    @Test
    @WithAnonymousUser
    public void testInvalidUsername() throws Exception {
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setUsername("da"); // Invalid username (too short)
        accountDTO.setPassword("1234567");
        accountDTO.setRole(Role.CUSTOMER);

        String accountJson = objectMapper.writeValueAsString(accountDTO);

        mockMvc.perform(post("/api/v1/auth/signup")
                        .contentType("application/json")
                        .content(accountJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser
    public void testInvalidPassword() throws Exception {
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setUsername("InvalidUsername");
        accountDTO.setPassword("12345"); // Invalid password (too short)
        accountDTO.setRole(Role.CUSTOMER);

        String accountJson = objectMapper.writeValueAsString(accountDTO);

        mockMvc.perform(post("/api/v1/auth/signup")
                        .contentType("application/json")
                        .content(accountJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Transactional
    @Rollback
    public void testValidAccount() throws Exception {
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setUsername("validUsername7");
        accountDTO.setPassword("validPassword123");
        accountDTO.setRole(Role.CUSTOMER);

        String accountJson = objectMapper.writeValueAsString(accountDTO);

        mockMvc.perform(post("/api/v1/auth/signup")
                        .contentType("application/json")
                        .content(accountJson))
                .andExpect(status().isCreated());
    }
}