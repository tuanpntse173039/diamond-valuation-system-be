package com.letitbee.diamondvaluationsystem.service.impl;

import com.letitbee.diamondvaluationsystem.entity.Account;
import com.letitbee.diamondvaluationsystem.enums.Role;
import com.letitbee.diamondvaluationsystem.exception.APIException;
import com.letitbee.diamondvaluationsystem.payload.AccountDTO;
import com.letitbee.diamondvaluationsystem.payload.RegisterDTO;
import com.letitbee.diamondvaluationsystem.repository.AccountRepository;
import com.letitbee.diamondvaluationsystem.service.AccountService;
import org.modelmapper.ModelMapper;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class AccountServiceImpl implements AccountService {
    private AccountRepository accountRepository;
//    private PasswordEncoder passwordEncoder;
    private ModelMapper mapper;

    public AccountServiceImpl(AccountRepository accountRepository, ModelMapper mapper) {
        this.accountRepository = accountRepository;
        this.mapper = mapper;
    }

    private AccountDTO mapToDto(Account account){
        AccountDTO accountDto = mapper.map(account, AccountDTO.class);
        return accountDto;
    }
    //convert DTO to Entity
    private Account mapToEntity(AccountDTO accountDto){
        Account account = mapper.map(accountDto, Account.class);
        return account;
    }

    @Override
    public AccountDTO createAccount(AccountDTO accountDto) {
        Account account = mapToEntity(accountDto);
        Account newAccount = accountRepository.save(account);
        return mapToDto(newAccount);
    }

    @Override
    public String login(AccountDTO accountDTO) {
        return "a";
    }

    @Override
    public String register(AccountDTO accountDTO) {
        //add check for username exists in database
        if (accountRepository.existsByName(accountDTO.getUsername())){
            throw new APIException(HttpStatus.BAD_REQUEST, "Account is already taken");
        }

        Account account = new Account();
        account.setName(accountDTO.getUsername());
        account.setPassword(accountDTO.getPassword());
        account.setRole(accountDTO.getRole());
        account.setIs_active(true);

        accountRepository.save(account);
        return "User registered successfully";
    }


}
