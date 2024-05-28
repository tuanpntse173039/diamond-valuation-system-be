package com.letitbee.diamondvaluationsystem.service;

import com.letitbee.diamondvaluationsystem.payload.AccountDTO;
import com.letitbee.diamondvaluationsystem.payload.RegisterDTO;

public interface AccountService {
    AccountDTO createAccount(AccountDTO accountDto);

    String login(AccountDTO accountDTO);

    String register(AccountDTO accountDTO);

}
