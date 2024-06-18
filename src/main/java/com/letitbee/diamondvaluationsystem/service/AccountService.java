package com.letitbee.diamondvaluationsystem.service;

import com.letitbee.diamondvaluationsystem.payload.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface AccountService {

    String login(AccountDTO accountDTO);

    AccountResponse register(CustomerRegisterDTO customerRegisterDTO);

    AccountResponse registerStaff(StaffRegisterDTO staffRegisterDTO);

    String updatePassword(String newPassword, Long id);

    AccountResponse updateAccount(Long id, AccountDTO accountDTO);

    LoginResponse login(HttpServletRequest request, HttpServletResponse response, AccountDTO accountDTO);

    LoginResponse refreshToken(HttpServletRequest request);
}
