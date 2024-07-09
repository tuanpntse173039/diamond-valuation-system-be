package com.letitbee.diamondvaluationsystem;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.letitbee.diamondvaluationsystem.entity.Account;
import com.letitbee.diamondvaluationsystem.entity.Customer;
import com.letitbee.diamondvaluationsystem.enums.Role;
import com.letitbee.diamondvaluationsystem.exception.ResourceNotFoundException;
import com.letitbee.diamondvaluationsystem.payload.AccountDTO;
import com.letitbee.diamondvaluationsystem.payload.AccountUpdate;
import com.letitbee.diamondvaluationsystem.payload.CustomerUpdate;
import com.letitbee.diamondvaluationsystem.repository.AccountRepository;
import com.letitbee.diamondvaluationsystem.repository.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(authorities = {"CUSTOMER"})
public class ManageAccount {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Test
    @Transactional
    @Rollback
    public void testOldPasswordNotCorrect() throws Exception {
        AccountUpdate accountUpdate = new AccountUpdate();
        accountUpdate.setOldPassword("123456");
        accountUpdate.setNewPassword("12345678");
        Account account = accountRepository.findById(26L).get();
        System.out.println(account.getPassword());
        System.out.println(passwordEncoder.encode(accountUpdate.getOldPassword()));
        String accountJson = objectMapper.writeValueAsString(accountUpdate);
        if(!passwordEncoder.matches(accountUpdate.getOldPassword(), account.getPassword())){
            mockMvc.perform(put("/api/v1/auth/26")
                            .contentType("application/json")
                            .content(accountJson))
                    .andExpect(status().isBadRequest());
            System.out.println("Old password not correct");

        }else{
            mockMvc.perform(put("/api/v1/auth/26")
                            .contentType("application/json")
                            .content(accountJson))
                    .andExpect(status().isOk());
            System.out.println("Update successfully");
        }
    }


    @Test
    @Transactional
    @Rollback
    public void testEmailInvalid() throws Exception {
        CustomerUpdate customerUpdate = new CustomerUpdate();
        customerUpdate.setNewEmail("testEmail");
        String accountJson = objectMapper.writeValueAsString(customerUpdate);

        mockMvc.perform(put("/api/v1/customers/5")
                        .contentType("application/json")
                        .content(accountJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Transactional
    @Rollback
    public void testEmailAlreadyExists() throws Exception {
        CustomerUpdate customerUpdate = new CustomerUpdate();
        customerUpdate.setNewEmail("junhib@gmail.com");
        String accountJson = objectMapper.writeValueAsString(customerUpdate);
        if(accountRepository.existsByEmail(customerUpdate.getNewEmail())){
            mockMvc.perform(put("/api/v1/customers/5")
                            .contentType("application/json")
                            .content(accountJson))
                    .andExpect(status().isBadRequest());
            System.out.println("Email already exists");
        }else{
            mockMvc.perform(put("/api/v1/customers/5")
                            .contentType("application/json")
                            .content(accountJson))
                    .andExpect(status().isOk());
            System.out.println("Update successfully");
        }
    }

}
