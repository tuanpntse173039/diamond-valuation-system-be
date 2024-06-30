package com.letitbee.diamondvaluationsystem.controller;

import com.letitbee.diamondvaluationsystem.entity.Account;
import com.letitbee.diamondvaluationsystem.entity.Customer;
import com.letitbee.diamondvaluationsystem.entity.RefreshToken;
import com.letitbee.diamondvaluationsystem.entity.Staff;
import com.letitbee.diamondvaluationsystem.enums.Role;
import com.letitbee.diamondvaluationsystem.payload.*;
import com.letitbee.diamondvaluationsystem.repository.AccountRepository;
import com.letitbee.diamondvaluationsystem.repository.CustomerRepository;
import com.letitbee.diamondvaluationsystem.repository.StaffRepository;
import com.letitbee.diamondvaluationsystem.security.JwtTokenProvider;
import com.letitbee.diamondvaluationsystem.service.AccountService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("api/v1/auth")
public class AuthController {
    private AccountService accountService;


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

    //register Staff
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
}
