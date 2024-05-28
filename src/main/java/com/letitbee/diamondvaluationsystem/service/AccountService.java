package com.letitbee.diamondvaluationsystem.service;

import com.letitbee.diamondvaluationsystem.payload.AccountDTO;

public interface AccountService {
    AccountDTO createAccount(AccountDTO accountDto);

    String login(AccountDTO accountDTO);

    String registerCustomer(AccountDTO accountDTO);

    String registerStaff(AccountDTO accountDTO);

}
