package com.letitbee.diamondvaluationsystem.service.impl;

import com.letitbee.diamondvaluationsystem.entity.Account;
import com.letitbee.diamondvaluationsystem.entity.Customer;
import com.letitbee.diamondvaluationsystem.entity.Staff;
import com.letitbee.diamondvaluationsystem.enums.Role;
import com.letitbee.diamondvaluationsystem.exception.APIException;
import com.letitbee.diamondvaluationsystem.exception.CredentialsException;
import com.letitbee.diamondvaluationsystem.exception.ResourceNotFoundException;
import com.letitbee.diamondvaluationsystem.payload.*;
import com.letitbee.diamondvaluationsystem.repository.AccountRepository;
import com.letitbee.diamondvaluationsystem.security.JwtTokenProvider;
import com.letitbee.diamondvaluationsystem.repository.CustomerRepository;
import com.letitbee.diamondvaluationsystem.repository.StaffRepository;
import com.letitbee.diamondvaluationsystem.service.AccountService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

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
    public LoginResponse login(HttpServletRequest request, HttpServletResponse response, AccountDTO accountDTO) {

        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(accountDTO.getUsernameOrEmail(), accountDTO.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            Account account = accountRepository.findByUsernameOrEmail(accountDTO.getUsernameOrEmail(), accountDTO.getUsernameOrEmail())
                    .orElseThrow(() -> new UsernameNotFoundException("User not found with username or email : " + accountDTO.getUsernameOrEmail()));
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
            String refreshToken = jwtTokenProvider.generateRefreshToken(authentication);
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

            loginResponse.setUserToken(jwtAuthResponse);
            return loginResponse;
        }catch (BadCredentialsException ex) {
            throw new CredentialsException(HttpStatus.UNAUTHORIZED, "Invalid username or password");
        }
    }


    @Override
    public String login(AccountDTO accountDTO) {
        return null;
    }

    @Override
    public AccountResponse register(CustomerRegisterDTO customerRegisterDTO) {
        //add check for username exists in database
        if (accountRepository.existsByUsername(customerRegisterDTO.getUsername())){
            throw new APIException(HttpStatus.BAD_REQUEST, "Account is already taken");
        }

        //save account to db
        Account account = new Account();
        account.setUsername(customerRegisterDTO.getUsername());
        account.setPassword(passwordEncoder.encode(customerRegisterDTO.getPassword()));
        account.setRole(Role.CUSTOMER);
        account.setIs_active(true);
        account.setEmail(customerRegisterDTO.getEmail());
        account = accountRepository.save(account);

        //save customer to db
        Customer customer = new Customer();
        customer.setFirstName(customerRegisterDTO.getFirstName());
        customer.setLastName(customerRegisterDTO.getLastName());
        customer.setPhone(customerRegisterDTO.getPhone());
        customer.setAddress(customerRegisterDTO.getAddress());
        customer.setIdentityDocument(customerRegisterDTO.getIdentityDocument());
        customer.setAvatar(customerRegisterDTO.getAvatar());
        customer.setAccount(account);
        customerRepository.save(customer);
        //return account to client without password
        AccountResponse newAccount = new AccountResponse();
        newAccount.setId(account.getId());
        newAccount.setUsername(account.getUsername());
        newAccount.setRole(account.getRole());
        newAccount.setIs_active(account.getIs_active());
        newAccount.setEmail(account.getEmail());
        return newAccount;
    }

    @Override
    public AccountResponse registerStaff(StaffRegisterDTO staffRegisterDTO) {
        if (accountRepository.existsByUsername(staffRegisterDTO.getUsername())){
            throw new APIException(HttpStatus.BAD_REQUEST, "Account is already taken");
        }

        //save account to db
        Account account = new Account();
        account.setUsername(staffRegisterDTO.getUsername());
        account.setPassword(passwordEncoder.encode(staffRegisterDTO.getPassword()));
        account.setRole(staffRegisterDTO.getRole());
        account.setIs_active(true);
        account.setEmail(staffRegisterDTO.getEmail());
        account = accountRepository.save(account);

        //save customer to db
        Staff staff = new Staff();
        staff.setFirstName(staffRegisterDTO.getFirstName());
        staff.setLastName(staffRegisterDTO.getLastName());
        staff.setPhone(staffRegisterDTO.getPhone());
        staff.setExperience(staffRegisterDTO.getExperience());
        staff.setCertificateLink(staffRegisterDTO.getCertificateLink());
        staff.setAccount(account);
        staffRepository.save(staff);

        //return account to client without password
        AccountResponse newAccount = new AccountResponse();
        newAccount.setId(account.getId());
        newAccount.setUsername(account.getUsername());
        newAccount.setRole(account.getRole());
        newAccount.setIs_active(account.getIs_active());
        newAccount.setEmail(account.getEmail());
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
        jwtAuthResponse.setRefreshToken(jwtTokenProvider.generateRefreshToken(authentication));
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