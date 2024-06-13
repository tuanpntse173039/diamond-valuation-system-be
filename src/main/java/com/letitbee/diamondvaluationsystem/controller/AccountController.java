package com.letitbee.diamondvaluationsystem.controller;

import com.letitbee.diamondvaluationsystem.payload.AccountDTO;
import com.letitbee.diamondvaluationsystem.payload.AccountResponse;
import com.letitbee.diamondvaluationsystem.payload.JwtAuthResponse;
import com.letitbee.diamondvaluationsystem.security.JwtTokenProvider;
import com.letitbee.diamondvaluationsystem.service.AccountService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("api/v1/accounts")
public class AccountController {
    private AccountService accountService;
    private JwtTokenProvider jwtTokenProvider;
    private AuthenticationManager authenticationManager;


    public AccountController(AccountService accountService,
                             JwtTokenProvider jwtTokenProvider, AuthenticationManager authenticationManager) {
        this.accountService = accountService;
        this.jwtTokenProvider = jwtTokenProvider;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping(value = {"/login", "/signin"})
    public ResponseEntity<JwtAuthResponse> login(@RequestBody AccountDTO accountDTO){
        ArrayList<String> token = accountService.login(accountDTO);
        JwtAuthResponse jwtAuthResponse = new JwtAuthResponse();
        jwtAuthResponse.setAccessToken(token.get(0));
        jwtAuthResponse.setRefreshToken(token.get(1));
        return ResponseEntity.ok(jwtAuthResponse);
    }

    //register Customer
    @PostMapping(value = {"/register", "/signup"})
    public ResponseEntity<AccountResponse> register(@RequestBody @Valid AccountDTO accountDTO){
        AccountResponse response = accountService.register(accountDTO);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updatePassword(@RequestBody String newPassword, @PathVariable(name = "id") long id){
        String response = accountService.updatePassword(newPassword, id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @PostMapping("/refresh-token")
    public ResponseEntity<?> refreshToken(HttpServletRequest request, HttpServletResponse response, @RequestBody AccountDTO accountDTO) {
        Cookie[] cookies = request.getCookies();
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(accountDTO.getUsername(), accountDTO.getPassword()));
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("refreshToken")) {
                    String refreshToken = cookie.getValue();
                    if (jwtTokenProvider.validateToken(refreshToken)) {
                        String newToken = jwtTokenProvider.generateToken(authentication);
                        return ResponseEntity.ok(newToken);
                    }
                }
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Refresh token is missing or invalid");
    }

}
