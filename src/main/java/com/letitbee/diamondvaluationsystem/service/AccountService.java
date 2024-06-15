package com.letitbee.diamondvaluationsystem.service;

import com.letitbee.diamondvaluationsystem.payload.AccountDTO;
import com.letitbee.diamondvaluationsystem.payload.AccountResponse;
import com.letitbee.diamondvaluationsystem.payload.LoginResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.net.http.HttpRequest;
import java.util.ArrayList;

public interface AccountService {

    LoginResponse login(HttpServletRequest request, HttpServletResponse response, AccountDTO accountDTO);

    AccountResponse register(AccountDTO accountDTO);

    String updatePassword(String newPassword, Long id);

    LoginResponse refreshToken(HttpServletRequest request);
}
