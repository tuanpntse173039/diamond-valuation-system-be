package com.letitbee.diamondvaluationsystem;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.letitbee.diamondvaluationsystem.entity.Account;
import com.letitbee.diamondvaluationsystem.enums.Role;
import com.letitbee.diamondvaluationsystem.payload.AccountDTO;
import com.letitbee.diamondvaluationsystem.repository.AccountRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser
public class ManageAccount {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private AccountRepository accountRepository;

    @Test
    @Transactional
    @Rollback
    public void testNewPasswordBlank() throws Exception {
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setUsername(accountRepository.findById(1L).get().getUsername());
        accountDTO.setPassword("");
        String accountJson = objectMapper.writeValueAsString(accountDTO);

        mockMvc.perform(put("/api/v1/accounts/password/1")
                        .contentType("application/json")
                        .content(accountJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Transactional
    @Rollback
    public void testNewPasswordIsNot6To24CharacterS() throws Exception {
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setUsername(accountRepository.findById(1L).get().getUsername());
        accountDTO.setPassword("test5");
        String accountJson = objectMapper.writeValueAsString(accountDTO);

        mockMvc.perform(put("/api/v1/accounts/password/1")
                        .contentType("application/json")
                        .content(accountJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Transactional
    @Rollback
    public void testNewPasswordNotHaveAtLeastANumber() throws Exception {
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setUsername(accountRepository.findById(1L).get().getUsername());
        accountDTO.setPassword("testPassword");
        String accountJson = objectMapper.writeValueAsString(accountDTO);

        mockMvc.perform(put("/api/v1/accounts/password/1")
                        .contentType("application/json")
                        .content(accountJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Transactional
    @Rollback
    public void testConfirmPasswordBlank() throws Exception {
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setUsername(accountRepository.findById(1L).get().getUsername());
        accountDTO.setPassword("");
        String accountJson = objectMapper.writeValueAsString(accountDTO);

        mockMvc.perform(put("/api/v1/accounts/password/1")
                        .contentType("application/json")
                        .content(accountJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Transactional
    @Rollback
    public void testConfirmPasswordNotMatch() throws Exception {
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setUsername(accountRepository.findById(1L).get().getUsername());
        accountDTO.setPassword("testPassword");
        String accountJson = objectMapper.writeValueAsString(accountDTO);

        mockMvc.perform(put("/api/v1/accounts/password/1")
                        .contentType("application/json")
                        .content(accountJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Transactional
    @Rollback
    public void testEmailBlank() throws Exception {
        AccountDTO accountDTO = new AccountDTO();
        Account account = accountRepository.findById(26L).get();
        accountDTO.setId(account.getId());
        accountDTO.setUsername(account.getUsername());
        accountDTO.setPassword(account.getPassword());
        accountDTO.setRole(account.getRole());
        accountDTO.setIs_active(account.getIs_active());
        accountDTO.setEmail("");
        String accountJson = objectMapper.writeValueAsString(accountDTO);

        mockMvc.perform(put("/api/v1/auth/26")
                        .contentType("application/json")
                        .content(accountJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Transactional
    @Rollback
    public void testEmailInvalid() throws Exception {
        AccountDTO accountDTO = new AccountDTO();
        Account account = accountRepository.findById(26L).get();
        accountDTO.setId(account.getId());
        accountDTO.setUsername(account.getUsername());
        accountDTO.setPassword(account.getPassword());
        accountDTO.setRole(account.getRole());
        accountDTO.setIs_active(account.getIs_active());
        accountDTO.setEmail("testEmail");
        String accountJson = objectMapper.writeValueAsString(accountDTO);

        mockMvc.perform(put("/api/v1/auth/26")
                        .contentType("application/json")
                        .content(accountJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Transactional
    @Rollback
    public void testEmailAlreadyExists() throws Exception {
        AccountDTO accountDTO = new AccountDTO();
        Account account = accountRepository.findById(36L).get();
        accountDTO.setId(account.getId());
        accountDTO.setUsername(account.getUsername());
        accountDTO.setPassword(account.getPassword());
        accountDTO.setRole(account.getRole());
        accountDTO.setIs_active(account.getIs_active());
        accountDTO.setEmail("tuanpham@gmail.com");
        Boolean ex = accountRepository.existsByEmail(accountDTO.getEmail());
        String accountJson = objectMapper.writeValueAsString(accountDTO);
        if(ex) {
            mockMvc.perform(put("/api/v1/auth/36")
                            .contentType("application/json")
                            .content(accountJson))
                    .andExpect(status().isBadRequest());
        }else{
            mockMvc.perform(put("/api/v1/auth/36")
                            .contentType("application/json")
                            .content(accountJson))
                    .andExpect(status().isOk());
        }
    }

}
