package com.letitbee.diamondvaluationsystem.service;

import com.letitbee.diamondvaluationsystem.payload.AccountDTO;
import com.letitbee.diamondvaluationsystem.payload.AccountResponse;

import java.util.ArrayList;

public interface AccountService {

    ArrayList<String> login(AccountDTO accountDTO);

    AccountResponse register(AccountDTO accountDTO);

    String updatePassword(String newPassword, Long id);

    ArrayList<String> refreshToken(AccountDTO accountDTO);
}
