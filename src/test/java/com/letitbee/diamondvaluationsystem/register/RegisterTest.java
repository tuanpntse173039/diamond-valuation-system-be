package com.letitbee.diamondvaluationsystem.register;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.letitbee.diamondvaluationsystem.controller.AccountController;
import com.letitbee.diamondvaluationsystem.enums.Role;
import com.letitbee.diamondvaluationsystem.payload.AccountDTO;
import com.letitbee.diamondvaluationsystem.service.AccountService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = AccountController.class)
public class RegisterTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AccountService accountService;

    @MockBean
    private AuthenticationManager authenticationManager;

    @MockBean
    private UserDetailsService customUserDetailsService;


    @Test
    public void testInvalidUsername() throws Exception {
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setUsername("da"); // Invalid username (too short)
        accountDTO.setPassword("1234567");
        accountDTO.setRole(Role.CUSTOMER);

        String accountJson = objectMapper.writeValueAsString(accountDTO);
        mockMvc.perform(post("/api/v1/accounts/signup")
                        .contentType("application/json")
                        .content(accountJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testInvalidPassword() throws Exception {
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setUsername("validUsername");
        accountDTO.setPassword("123"); // Invalid password (too short)
        accountDTO.setRole(Role.CUSTOMER);

        String accountJson = objectMapper.writeValueAsString(accountDTO);
        mockMvc.perform(post("/api/v1/accounts/signup")
                        .contentType("application/json")
                        .content(accountJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testValidAccount() throws Exception {
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setUsername("validUsername");
        accountDTO.setPassword("validPassword123");
        accountDTO.setRole(Role.CUSTOMER);

        String accountJson = objectMapper.writeValueAsString(accountDTO);

        mockMvc.perform(post("/api/v1/accounts/signup")
                        .contentType("application/json")
                        .content(accountJson))
                .andExpect(status().isCreated());
    }


}
