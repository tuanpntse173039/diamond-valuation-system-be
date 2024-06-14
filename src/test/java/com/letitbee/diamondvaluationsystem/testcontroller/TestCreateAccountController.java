package com.letitbee.diamondvaluationsystem.testcontroller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.letitbee.diamondvaluationsystem.controller.AuthController;
import com.letitbee.diamondvaluationsystem.enums.Role;
import com.letitbee.diamondvaluationsystem.payload.AccountDTO;
import com.letitbee.diamondvaluationsystem.security.JwtAuthenticationFilter;
import com.letitbee.diamondvaluationsystem.security.JwtTokenProvider;
import com.letitbee.diamondvaluationsystem.service.AccountService;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = AuthController.class)
public class TestCreateAccountController {

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

    @MockBean
    private AuthController authController;

    @MockBean
    private JwtTokenProvider jwtTokenProvider;

    @MockBean
    private JwtAuthenticationFilter jwtAuthenticationFilter;
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
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
    public void testInvalidPassword() throws Exception {
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setUsername("validUsername");
        accountDTO.setPassword("123"); // Invalid password (too short)
        accountDTO.setRole(Role.CUSTOMER);

        String accountJson = objectMapper.writeValueAsString(accountDTO);

        mockMvc.perform(post("/api/v1/auth/signup")
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

        mockMvc.perform(post("/api/v1/auth/signup")
                        .contentType("application/json")
                        .content(accountJson))
                .andExpect(status().isCreated());
    }

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before("setup")
    public void setup()
    {
        //Init MockMvc Object and build
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }
}
