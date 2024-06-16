package com.letitbee.diamondvaluationsystem;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.letitbee.diamondvaluationsystem.entity.Customer;
import com.letitbee.diamondvaluationsystem.enums.Role;
import com.letitbee.diamondvaluationsystem.payload.AccountDTO;
import com.letitbee.diamondvaluationsystem.payload.AccountResponse;
import com.letitbee.diamondvaluationsystem.payload.CustomerDTO;
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

    @Test
    @Transactional
    @Rollback
    public void testFirstnameBlank() throws Exception {

        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setFirstName("");
        customerDTO.setLastName("testLastname");
        customerDTO.setPhone("testPhone");
        customerDTO.setAddress("testAddress");
        AccountResponse accountResponse = new AccountResponse();
        accountResponse.setId(Long.valueOf(40));
        customerDTO.setAccount(accountResponse);

        String customerJson = objectMapper.writeValueAsString(customerDTO);

        mockMvc.perform(post("/api/v1/customers")
                        .contentType("application/json")
                        .content(customerJson))
                .andExpect(status().isBadRequest());
    }
    @Test
    @Transactional
    @Rollback
    public void testFirstnameContainsInvalidCharacters() throws Exception {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setFirstName("John123"); // Invalid first name
        customerDTO.setLastName("Doe");
        customerDTO.setPhone("1234567890");
        customerDTO.setAddress("testAddress");
        customerDTO.setEmail("test@example.com");
        customerDTO.setIdentityDocument("123456789012");
        AccountResponse accountResponse = new AccountResponse();
        accountResponse.setId(40L);
        customerDTO.setAccount(accountResponse);

        String customerJson = objectMapper.writeValueAsString(customerDTO);

        mockMvc.perform(post("/api/v1/customers")
                        .contentType("application/json")
                        .content(customerJson))
                .andExpect(status().isBadRequest());
    }

}
