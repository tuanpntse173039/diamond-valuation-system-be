package com.letitbee.diamondvaluationsystem.service;

import com.letitbee.diamondvaluationsystem.payload.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.net.http.HttpRequest;
import java.util.ArrayList;

public interface AccountService {

    LoginResponse login(HttpServletRequest request, HttpServletResponse response, AccountDTO accountDTO);

    AccountResponse register(CustomerRegisterDTO customerRegisterDTO);

    AccountResponse registerStaff(StaffRegisterDTO staffRegisterDTO);

    String updatePassword(String newPassword, Long id);

    LoginResponse refreshToken(HttpServletRequest request);
}
