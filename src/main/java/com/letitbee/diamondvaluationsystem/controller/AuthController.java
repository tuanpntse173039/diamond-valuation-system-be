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
    public ResponseEntity<LoginResponse> login(HttpServletRequest request,@RequestBody AccountDTO accountDTO){
        ArrayList<String> token = accountService.login(request,accountDTO);
        JwtAuthResponse jwtAuthResponse = new JwtAuthResponse();
        jwtAuthResponse.setAccessToken(token.get(0));
        jwtAuthResponse.setRefreshToken(token.get(1));
        Account account = accountRepository.findByUsername(accountDTO.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username or email : " + accountDTO.getUsername()));
        LoginResponse loginResponse = new LoginResponse();
        if(account.getRole().equals(Role.CUSTOMER)){
            Customer customer = customerRepository.findCustomerByAccount_Id(account.getId());
            loginResponse.setDto(mapper.map(customer, CustomerDTO.class));
        }else{
            Staff staff = staffRepository.findStaffByAccount_Id(account.getId());
            loginResponse.setDto(mapper.map(staff, StaffDTO.class));
        }
        loginResponse.setJwtAuthResponse(jwtAuthResponse);
        return ResponseEntity.ok(loginResponse);
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
    public ResponseEntity<JwtAuthResponse> refreshToken(@RequestBody String refreshToken) {
        ArrayList<String> token = accountService.refreshToken(refreshToken);
        JwtAuthResponse jwtAuthResponse = new JwtAuthResponse();
        jwtAuthResponse.setAccessToken(token.get(0));
        jwtAuthResponse.setRefreshToken(token.get(1));
        return ResponseEntity.ok(jwtAuthResponse);
    }
}
