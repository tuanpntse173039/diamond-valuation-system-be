package com.letitbee.diamondvaluationsystem.controller;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.letitbee.diamondvaluationsystem.entity.RefreshToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.letitbee.diamondvaluationsystem.payload.*;
import com.letitbee.diamondvaluationsystem.service.AccountService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("api/v1/auth")
public class AuthController {
    private AccountService accountService;
    @Value("${spring.security.oauth2.client.registration.google.client-id}")
    private String clientId;

    public AuthController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping(value = {"/login", "/signin"})
    public ResponseEntity<LoginResponse> login( @RequestBody AccountDTO accountDTO){
        return ResponseEntity.ok(accountService.login(accountDTO));
    }

    //register Customer
    @PostMapping(value = {"/register", "/signup"})
    public ResponseEntity<AccountResponse> register(@RequestBody @Valid CustomerRegisterDTO customerRegisterDTO){
        AccountResponse response = accountService.register(customerRegisterDTO);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
    @PostMapping("/verify-account")
    public ResponseEntity<String> verifyAccount(@RequestParam(name = "token") String code) {
        accountService.verifyAccount(code);
        return ResponseEntity.ok("Account verified successfully");
    }

    //register Staff
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @PostMapping(value = {"/register-staff", "/signup-staff"})
    public ResponseEntity<AccountResponse> registerStaff(@RequestBody @Valid StaffRegisterDTO staffRegisterDTO){
        AccountResponse response = accountService.registerStaff(staffRegisterDTO);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AccountResponse> changePassword(@RequestBody @Valid AccountUpdate accountUpdate, @PathVariable(name = "id") long id){
        return new ResponseEntity<>(accountService.changePassword(accountUpdate,id), HttpStatus.OK);
    }
    @PostMapping("/refresh-token")
    public ResponseEntity<JwtAuthResponse> refreshToken(@RequestBody RefreshToken refreshToken) {
        return ResponseEntity.ok(accountService.refreshToken(refreshToken));
    }

    @GetMapping("/forget-password")
    public ResponseEntity<String> forgetPasswordPage() {
        return ResponseEntity.ok("Forgot Page");
    }
    @PostMapping("/forget-password")
    public ResponseEntity<String> forgetPassword(@RequestBody Map<String, String> request) {
        String email = request.get("email").trim();
        email = email.replaceAll("\"", "");
        accountService.forgetPassword(email);
        return ResponseEntity.ok("Email sent successfully");
    }


    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestParam(name = "token") String code,@RequestBody Map<String, String> request) {
        String newPassword = request.get("newPassword").trim();
        newPassword = newPassword.replaceAll("\"", "");
        accountService.resetPassword(code, newPassword);
        return ResponseEntity.ok("Password reset successfully");
    }

    @PostMapping("/google-login")
    public ResponseEntity<LoginResponse> googleLogin(@RequestBody Map<String, String> request) {
        String token = request.get("token");
        System.out.println(clientId);
        try {
            // Log the received token for debugging
            System.out.println("Received ID token: " + token);

            GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(GoogleNetHttpTransport.newTrustedTransport(), JacksonFactory.getDefaultInstance())
                    .setAudience(Collections.singletonList(clientId))
                    .build();

            GoogleIdToken idToken = verifier.verify(token);
            if (idToken != null) {
                GoogleIdToken.Payload payload = idToken.getPayload();
                String email = payload.getEmail();

                // Log the verified email for debugging
                System.out.println("Verified email: " + email);

                LoginResponse loginResponse = accountService.findAccountByEmail(email);
                return ResponseEntity.ok(loginResponse);
            } else {
                throw new RuntimeException("Invalid ID token.");
            }
        }  catch (GeneralSecurityException | IOException e) {
            throw new RuntimeException("Error verifying token", e);
        }
    }
}
