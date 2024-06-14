package com.letitbee.diamondvaluationsystem.service;

import com.letitbee.diamondvaluationsystem.payload.AccountDTO;
import com.letitbee.diamondvaluationsystem.payload.AccountResponse;
import jakarta.servlet.http.HttpServletRequest;

import java.net.http.HttpRequest;
import java.util.ArrayList;

public interface AccountService {

    ArrayList<String> login(HttpServletRequest request,AccountDTO accountDTO);

    AccountResponse register(AccountDTO accountDTO);

    String updatePassword(String newPassword, Long id);

    ArrayList<String> refreshToken(String refreshToken);
}
