package com.letitbee.diamondvaluationsystem.service.impl;

import com.letitbee.diamondvaluationsystem.entity.Account;
import com.letitbee.diamondvaluationsystem.exception.APIException;
import com.letitbee.diamondvaluationsystem.exception.ResourceNotFoundException;
import com.letitbee.diamondvaluationsystem.payload.AccountDTO;
import com.letitbee.diamondvaluationsystem.payload.AccountResponse;
import com.letitbee.diamondvaluationsystem.repository.AccountRepository;
//import com.letitbee.diamondvaluationsystem.security.JwtTokenProvider;
import com.letitbee.diamondvaluationsystem.service.AccountService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl implements AccountService {
    private AccountRepository accountRepository;
//    private PasswordEncoder passwordEncoder;
//    private JwtTokenProvider jwtTokenProvider;
//    private AuthenticationManager authenticationManager;

    private ModelMapper mapper;

    public AccountServiceImpl(AccountRepository accountRepository, ModelMapper mapper
                              //JwtTokenProvider jwtTokenProvider, AuthenticationManager authenticationManager,
                             /* PasswordEncoder passwordEncoder*/) {
        this.accountRepository = accountRepository;
        this.mapper = mapper;
//        this.jwtTokenProvider = jwtTokenProvider;
//        this.authenticationManager = authenticationManager;
//        this.passwordEncoder = passwordEncoder;
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
    public LoginResponse login(HttpServletRequest request, HttpServletResponse response, AccountDTO accountDTO) {

        try {
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
            String refreshToken = jwtTokenProvider.generateRefreshToken();
            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if (cookie.getName().equals("refreshToken")) {
                        refreshToken = cookie.getValue();
                        jwtAuthResponse.setRefreshToken(refreshToken);
                    }
                }
            } else {
                jwtAuthResponse.setRefreshToken(refreshToken);
            }
            loginResponse.setUserToken(jwtAuthResponse);
            // Set refresh token cookie in the response
            Cookie refreshTokenCookie = new Cookie("refreshToken", refreshToken);
            refreshTokenCookie.setHttpOnly(true);
            refreshTokenCookie.setPath("/");
            refreshTokenCookie.setMaxAge(24 * 60 * 60 * 30);
            response.addCookie(refreshTokenCookie);

            jwtAuthResponse.setRefreshToken(refreshToken);
            loginResponse.setUserToken(jwtAuthResponse);
            return loginResponse;
        }catch (BadCredentialsException ex) {
            throw new CredentialsException(HttpStatus.UNAUTHORIZED, "Invalid username or password");
        }
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
//        account.setPassword(passwordEncoder.encode(accountDTO.getPassword()));
        account.setPassword(accountDTO.getPassword());
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
    public LoginResponse refreshToken(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        String refreshToken = null;
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("refreshToken")) {
                    refreshToken = cookie.getValue();
                }
            }
        }
        if (refreshToken == null) {
            throw new APIException(HttpStatus.BAD_REQUEST, "Refresh token is missing");
        }
        if (!jwtTokenProvider.validateToken(refreshToken)) {
            throw new APIException(HttpStatus.BAD_REQUEST, "Invalid refresh token");
        }
        String accessToken = request.getHeader("Authorization");
        if (accessToken != null && accessToken.startsWith("Bearer ")) {
            accessToken = accessToken.substring(7);  // Correct substring index
        }
        String username = jwtTokenProvider.getUsername(accessToken);
        Account account = accountRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username or email : " + username));
        Authentication authentication = new UsernamePasswordAuthenticationToken(account.getUsername(), account.getPassword());
        SecurityContextHolder.getContext().setAuthentication(authentication);
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
        jwtAuthResponse.setRefreshToken(jwtTokenProvider.generateRefreshToken());
        loginResponse.setUserToken(jwtAuthResponse);
        return loginResponse;
    }
    @Override
    public AccountResponse updateAccount(Long id, AccountDTO accountDTO) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Account", "id", String.valueOf(id)));
        account.setUsername(accountDTO.getUsername());
        account.setPassword(accountDTO.getPassword());
        account.setRole(accountDTO.getRole());
        account.setIs_active(accountDTO.getIs_active());
        accountRepository.save(account);

        AccountResponse newAccount = new AccountResponse();
        newAccount.setId(account.getId());
        newAccount.setUsername(account.getUsername());
        newAccount.setRole(account.getRole());
        newAccount.setIs_active(account.getIs_active());
        return newAccount;
    }

}