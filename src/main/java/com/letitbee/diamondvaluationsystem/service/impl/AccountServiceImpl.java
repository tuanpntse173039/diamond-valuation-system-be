package com.letitbee.diamondvaluationsystem.service.impl;

import com.letitbee.diamondvaluationsystem.entity.Account;
import com.letitbee.diamondvaluationsystem.payload.AccountDTO;
import com.letitbee.diamondvaluationsystem.repository.AccountRepository;
import com.letitbee.diamondvaluationsystem.service.AccountService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl implements AccountService {
    private AccountRepository accountRepository;
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
        return "";
    }

    @Override
    public String registerCustomer(AccountDTO accountDTO) {
        return "";
    }

    @Override
    public String registerStaff(AccountDTO accountDTO) {
        return "";
    }

}
