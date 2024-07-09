package com.letitbee.diamondvaluationsystem.service;

import com.letitbee.diamondvaluationsystem.entity.RefreshToken;
import com.letitbee.diamondvaluationsystem.payload.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;

public interface AccountService {

    LoginResponse login(AccountDTO accountDTO);

    AccountResponse register(CustomerRegisterDTO customerRegisterDTO);

    AccountResponse registerStaff(StaffRegisterDTO staffRegisterDTO);

    AccountResponse changePassword(AccountUpdate accountUpdate, Long id);

    void forgetPassword(String email);

    void resetPassword(String code, String newPassword);

    JwtAuthResponse refreshToken(RefreshToken refreshToken);

    void verifyAccount(String code);
}
