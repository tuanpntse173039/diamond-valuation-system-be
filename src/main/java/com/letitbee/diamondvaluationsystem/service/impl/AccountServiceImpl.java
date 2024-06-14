package com.letitbee.diamondvaluationsystem.service.impl;

import com.letitbee.diamondvaluationsystem.entity.Account;
import com.letitbee.diamondvaluationsystem.entity.Customer;
import com.letitbee.diamondvaluationsystem.entity.Staff;
import com.letitbee.diamondvaluationsystem.enums.Role;
import com.letitbee.diamondvaluationsystem.exception.APIException;
import com.letitbee.diamondvaluationsystem.exception.ResourceNotFoundException;
import com.letitbee.diamondvaluationsystem.payload.*;
import com.letitbee.diamondvaluationsystem.repository.AccountRepository;
import com.letitbee.diamondvaluationsystem.repository.CustomerRepository;
import com.letitbee.diamondvaluationsystem.repository.StaffRepository;
import com.letitbee.diamondvaluationsystem.security.JwtTokenProvider;
import com.letitbee.diamondvaluationsystem.service.AccountService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class AccountServiceImpl implements AccountService {
    private AccountRepository accountRepository;
    private PasswordEncoder passwordEncoder;
    private JwtTokenProvider jwtTokenProvider;
    private AuthenticationManager authenticationManager;
    private CustomerRepository customerRepository;
    private StaffRepository staffRepository;

    private ModelMapper mapper;

    public AccountServiceImpl(AccountRepository accountRepository, ModelMapper mapper,
                              JwtTokenProvider jwtTokenProvider, AuthenticationManager authenticationManager,
                             PasswordEncoder passwordEncoder, CustomerRepository customerRepository, StaffRepository staffRepository) {
        this.accountRepository = accountRepository;
        this.mapper = mapper;
        this.jwtTokenProvider = jwtTokenProvider;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.customerRepository = customerRepository;
        this.staffRepository = staffRepository;
    }
    private AccountDTO mapToDto(Account account){
        AccountDTO accountDto = mapper.map(account, AccountDTO.class);
        return accountDto;
    }
    //convert DTO to Entity
    private Account mapToEntity(AccountDTO accountDto){
        Account account = mapper.map(accountDto, Account.class);
        return account;
    }

    @Override
    public LoginResponse login(HttpServletRequest request, AccountDTO accountDTO) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(accountDTO.getUsername(), accountDTO.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        Account account = accountRepository.findByUsername(accountDTO.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username or email : " + accountDTO.getUsername()));
        LoginResponse loginResponse = new LoginResponse();

        if (account.getRole().equals(Role.CUSTOMER)) {
            Customer customer = customerRepository.findCustomerByAccount_Id(account.getId());
            loginResponse.setUserInformation(customer == null ? null : mapper.map(customer, CustomerDTO.class));
        } else {
            Staff staff = staffRepository.findStaffByAccount_Id(account.getId());
            loginResponse.setUserInformation(staff == null ? null : mapper.map(staff, StaffDTO.class));
        }
        JwtAuthResponse jwtAuthResponse = new JwtAuthResponse();
        jwtAuthResponse.setAccessToken(jwtTokenProvider.generateToken(authentication));
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("refreshToken")) {
                    String refreshToken = cookie.getValue();
                    jwtAuthResponse.setRefreshToken(refreshToken);
                }
            }
        }
        else {
            jwtAuthResponse.setRefreshToken(jwtTokenProvider.generateRefreshToken(authentication));
        }
        loginResponse.setUserToken(jwtAuthResponse);
        return loginResponse;
    }



    @Override
    public AccountResponse register(AccountDTO accountDTO) {
        //add check for username exists in database
        if (accountRepository.existsByUsername(accountDTO.getUsername())){
            throw new APIException(HttpStatus.BAD_REQUEST, "Account is already taken");
        }

        //save account to db
        Account account = new Account();
        account.setUsername(accountDTO.getUsername());
        account.setPassword(passwordEncoder.encode(accountDTO.getPassword()));
//        account.setPassword(accountDTO.getPassword());
        account.setRole(accountDTO.getRole());
        account.setIs_active(true);
        account = accountRepository.save(account);

        //return account to client without password
        AccountResponse newAccount = new AccountResponse();
        newAccount.setId(account.getId());
        newAccount.setUsername(account.getUsername());
        newAccount.setRole(account.getRole());
        newAccount.setIs_active(account.getIs_active());
        return newAccount;
    }

    @Override
    public String updatePassword(String newPassword, Long id) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Account", "id", String.valueOf(id)));
        //happycase
        account.setPassword(newPassword);
        accountRepository.save(account);
        return "Update password successfully";
    }

    @Override
    public ArrayList<String> refreshToken(String refreshToken) {
        String username = jwtTokenProvider.getUsername(refreshToken);
        Account account = accountRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("Account", "username", username));
        AccountDTO accountDTO = mapToDto(account);
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(accountDTO.getUsername(), accountDTO.getPassword()));
        ArrayList<String> response = new ArrayList<>();
        if (jwtTokenProvider.validateToken(refreshToken)) {
            response.add(jwtTokenProvider.generateToken(authentication));
            response.add(jwtTokenProvider.generateRefreshToken(authentication));
            return response;
        }
        return null;
    }
}