package com.letitbee.diamondvaluationsystem.controller;

import com.letitbee.diamondvaluationsystem.entity.Account;
import com.letitbee.diamondvaluationsystem.entity.Customer;
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
    private JwtTokenProvider jwtTokenProvider;
    private AuthenticationManager authenticationManager;
    private AccountRepository accountRepository;
    private CustomerRepository customerRepository;
    private StaffRepository staffRepository;
    private ModelMapper mapper;


    public AuthController(AccountService accountService,
                          JwtTokenProvider jwtTokenProvider, AuthenticationManager authenticationManager
                          , AccountRepository accountRepository,
                          ModelMapper mapper
                          , CustomerRepository customerRepository, StaffRepository staffRepository) {
        this.accountService = accountService;
        this.jwtTokenProvider = jwtTokenProvider;
        this.authenticationManager = authenticationManager;
        this.accountRepository = accountRepository;
        this.mapper = mapper;
        this.customerRepository = customerRepository;
        this.staffRepository = staffRepository;
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
    public ResponseEntity<String> updatePassword(@RequestBody String newPassword, @PathVariable(name = "id") long id){
        String response = accountService.updatePassword(newPassword, id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @PostMapping("/refresh-token")
    public ResponseEntity<LoginResponse> refreshToken(HttpServletRequest request) {
        return ResponseEntity.ok(accountService.refreshToken(request));
    }
}
