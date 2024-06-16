package com.letitbee.diamondvaluationsystem.service;

import com.letitbee.diamondvaluationsystem.payload.AccountDTO;
import com.letitbee.diamondvaluationsystem.payload.AccountResponse;
import com.letitbee.diamondvaluationsystem.payload.LoginResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface AccountService {

    String login(AccountDTO accountDTO);

    AccountResponse register(AccountDTO accountDTO);

    String updatePassword(String newPassword, Long id);

    AccountResponse updateAccount(Long id, AccountDTO accountDTO);

    LoginResponse login(HttpServletRequest request, HttpServletResponse response, AccountDTO accountDTO);

    LoginResponse refreshToken(HttpServletRequest request);
}
