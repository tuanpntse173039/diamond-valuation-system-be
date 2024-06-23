package com.letitbee.diamondvaluationsystem.service;

import com.letitbee.diamondvaluationsystem.entity.RefreshToken;
import com.letitbee.diamondvaluationsystem.payload.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;

public interface AccountService {

    LoginResponse login(AccountDTO accountDTO);

    AccountResponse register(CustomerRegisterDTO customerRegisterDTO);

    AccountResponse registerStaff(StaffRegisterDTO staffRegisterDTO);

    String updatePassword(AccountDTO accountDTO, Long id);

    JwtAuthResponse refreshToken(RefreshToken refreshToken);
}
