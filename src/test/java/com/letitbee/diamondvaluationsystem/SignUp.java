package com.letitbee.diamondvaluationsystem;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.letitbee.diamondvaluationsystem.enums.Role;
import com.letitbee.diamondvaluationsystem.payload.AccountDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
public class SignUp {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    @Transactional
    @Rollback
    public void testUsernameBlank() throws Exception {
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setUsername("");
        accountDTO.setPassword("testPassword");
        accountDTO.setRole(Role.CUSTOMER);
        String accountJson = objectMapper.writeValueAsString(accountDTO);

        mockMvc.perform(post("/api/v1/accounts/signup")
                        .contentType("application/json")
                        .content(accountJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Transactional
    @Rollback
    public void testUsernameIsNot6To24CharacterS() throws Exception {
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setUsername("test5");
        accountDTO.setPassword("testPassword");
        accountDTO.setRole(Role.CUSTOMER);
        String accountJson = objectMapper.writeValueAsString(accountDTO);

        mockMvc.perform(post("/api/v1/accounts/signup")
                        .contentType("application/json")
                        .content(accountJson))
                .andExpect(status().isBadRequest());

    }

    @Test
    @Transactional
    @Rollback
    public void testUsernameAlreadyExists() throws Exception {
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setUsername("customer");
        accountDTO.setPassword("testPassword");
        accountDTO.setRole(Role.CUSTOMER);
        String accountJson = objectMapper.writeValueAsString(accountDTO);

        mockMvc.perform(post("/api/v1/accounts/signup")
                        .contentType("application/json")
                        .content(accountJson))
                .andExpect(status().isBadRequest());

    }

}
