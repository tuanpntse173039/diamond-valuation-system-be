package com.letitbee.diamondvaluationsystem.service.impl;

import com.letitbee.diamondvaluationsystem.entity.Account;
import com.letitbee.diamondvaluationsystem.entity.Customer;
import com.letitbee.diamondvaluationsystem.entity.RefreshToken;
import com.letitbee.diamondvaluationsystem.entity.Staff;
import com.letitbee.diamondvaluationsystem.enums.Role;
import com.letitbee.diamondvaluationsystem.exception.APIException;
import com.letitbee.diamondvaluationsystem.exception.CredentialsException;
import com.letitbee.diamondvaluationsystem.exception.ResourceNotFoundException;
import com.letitbee.diamondvaluationsystem.payload.*;
import com.letitbee.diamondvaluationsystem.repository.AccountRepository;
import com.letitbee.diamondvaluationsystem.repository.CustomerRepository;
import com.letitbee.diamondvaluationsystem.repository.RefreshTokenRepository;
import com.letitbee.diamondvaluationsystem.repository.StaffRepository;
import com.letitbee.diamondvaluationsystem.security.JwtTokenProvider;
import com.letitbee.diamondvaluationsystem.service.AccountService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

@Service
public class AccountServiceImpl implements AccountService {
    private AccountRepository accountRepository;
    private PasswordEncoder passwordEncoder;
    private JwtTokenProvider jwtTokenProvider;
    private AuthenticationManager authenticationManager;
    private CustomerRepository customerRepository;
    private StaffRepository staffRepository;
    private RefreshTokenRepository refreshTokenRepository;
    private ModelMapper mapper;

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${app-jwt-expiration-refresh-token-milliseconds}")
    private long jwtExpirationRefreshDate;

    public AccountServiceImpl(AccountRepository accountRepository, ModelMapper mapper,
                              JwtTokenProvider jwtTokenProvider, AuthenticationManager authenticationManager,
                             PasswordEncoder passwordEncoder, CustomerRepository customerRepository,
                              StaffRepository staffRepository,
                              RefreshTokenRepository refreshTokenRepository) {
        this.accountRepository = accountRepository;
        this.mapper = mapper;
        this.jwtTokenProvider = jwtTokenProvider;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.customerRepository = customerRepository;
        this.staffRepository = staffRepository;
        this.refreshTokenRepository = refreshTokenRepository;
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
    public LoginResponse login(AccountDTO accountDTO) {

        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(accountDTO.getUsernameOrEmail(), accountDTO.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            Account account = accountRepository.findByUsernameOrEmail(accountDTO.getUsernameOrEmail(), accountDTO.getUsernameOrEmail())
                    .orElseThrow(() -> new UsernameNotFoundException("User not found with username or email : " + accountDTO.getUsernameOrEmail()));
            LoginResponse loginResponse = new LoginResponse();

            if (account.getRole().equals(Role.CUSTOMER)) {
                Customer customer = customerRepository.findCustomerByAccount_Id(account.getId());
                if(customer == null){
                    throw new APIException(HttpStatus.BAD_REQUEST, "Customer not found");
                }
                loginResponse.setCustomerOrStaffId(customer.getId());
                loginResponse.setFirstName(customer.getFirstName());
                loginResponse.setLastName(customer.getLastName());
                loginResponse.setUsername(account.getUsername());
                loginResponse.setEmail(account.getEmail());
                loginResponse.setRole(account.getRole());

            } else {
                Staff staff = staffRepository.findStaffByAccount_Id(account.getId());
                if(staff == null){
                    throw new APIException(HttpStatus.BAD_REQUEST, "Staff not found");
                }
                loginResponse.setCustomerOrStaffId(staff.getId());
                loginResponse.setFirstName(staff.getFirstName());
                loginResponse.setLastName(staff.getLastName());
                loginResponse.setUsername(account.getUsername());
                loginResponse.setEmail(account.getEmail());
                loginResponse.setRole(account.getRole());
            }
            JwtAuthResponse jwtAuthResponse = new JwtAuthResponse();
            jwtAuthResponse.setAccessToken(jwtTokenProvider.generateToken(authentication));
            String refreshToken = null;
            RefreshToken token = refreshTokenRepository.findByAccount(account)
                    .orElse(new RefreshToken());
            if(token.getToken() != null){
                refreshToken = token.getToken();
                jwtAuthResponse.setRefreshToken(refreshToken);
            }else {
                refreshToken = UUID.randomUUID().toString();
                token.setToken(refreshToken);
                long currentTimeMillis = System.currentTimeMillis();
                long expirationTimeMillis = currentTimeMillis + jwtExpirationRefreshDate;
                Date expiryDate = new Date(expirationTimeMillis);
                token.setExpiryDate(expiryDate);
                token.setAccount(account);
                refreshTokenRepository.save(token);
                jwtAuthResponse.setRefreshToken(refreshToken);
            }
            loginResponse.setUserToken(jwtAuthResponse);

            return loginResponse;
        }catch (BadCredentialsException ex) {
            throw new CredentialsException(HttpStatus.UNAUTHORIZED, "Invalid username or password");
        }
    }



    @Override
    public AccountResponse register(CustomerRegisterDTO customerRegisterDTO) {
        //add check for username exists in database
        if (accountRepository.existsByUsernameOrEmail(customerRegisterDTO.getUsername(), customerRegisterDTO.getEmail())){
            throw new APIException(HttpStatus.BAD_REQUEST, "Username or email is already taken");
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

        try {
            sendVerificationEmail(customerRegisterDTO, "https://www.hntdiamond.store/");
        } catch (MessagingException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return newAccount;
    }

    private void sendVerificationEmail(CustomerRegisterDTO customerRegisterDTO, String siteURL) throws MessagingException, UnsupportedEncodingException {
        String subject = "Please verify your registration";
        String senderName = "H&T Diamond";
        String mailContent = "<p>Dear " + customerRegisterDTO.getFirstName() + " " + customerRegisterDTO.getLastName() + ",</p>";
        mailContent += "<p>Thank you for registering with H&T Diamond. Please click the link below to verify your registration:</p>";

        mailContent += "<h3><a href=\"" + siteURL + "\">Verify your account</a></h3>";

        mailContent += "<p>Thank you,<br>The H&T Diamond Team</p>";

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        helper.setFrom("hungdmse173190@fpt.edu.vn", senderName);
        helper.setTo(customerRegisterDTO.getEmail());
        helper.setSubject(subject);
        helper.setText(mailContent, true);
        javaMailSender.send(message);
    }

    @Override
    public AccountResponse registerStaff(StaffRegisterDTO staffRegisterDTO) {
        if (accountRepository.existsByUsernameOrEmail(staffRegisterDTO.getUsername(), staffRegisterDTO.getEmail())){
            throw new APIException(HttpStatus.BAD_REQUEST, "Username or email is already taken");
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
    public String update(AccountDTO accountDTO, Long id) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Account", "id", String.valueOf(id)));
        //happycase
        if(accountRepository.existsByUsernameOrEmail(accountDTO.getUsername(), accountDTO.getEmail())){
            throw new APIException(HttpStatus.BAD_REQUEST, "Username or email is already taken");
        }
        account.setUsername(accountDTO.getUsername());
        account.setEmail(accountDTO.getEmail());
        account.setPassword(passwordEncoder.encode(accountDTO.getPassword()));
        accountRepository.save(account);
        return "Update password successfully";
    }

    @Override
    public JwtAuthResponse refreshToken(RefreshToken refreshToken) {
        RefreshToken token = refreshTokenRepository.findByToken(refreshToken.getToken())
                .orElseThrow(() -> new APIException(HttpStatus.BAD_REQUEST, "Invalid refresh token"));
        if(token.getExpiryDate().compareTo(new Date()) < 0){
            throw new APIException(HttpStatus.BAD_REQUEST, "Refresh token is expired");
        }
        JwtAuthResponse jwtAuthResponse = new JwtAuthResponse();
        jwtAuthResponse.setAccessToken(jwtTokenProvider.generateTokenWithUsername(token.getAccount().getUsername()));
        String refreshToken1 = UUID.randomUUID().toString();
        jwtAuthResponse.setRefreshToken(refreshToken1);
        token.setToken(refreshToken1);
        long currentTimeMillis = System.currentTimeMillis();
        long expirationTimeMillis = currentTimeMillis + jwtExpirationRefreshDate;
        Date expiryDate = new Date(expirationTimeMillis);
        token.setExpiryDate(expiryDate);
        refreshTokenRepository.save(token);
        return jwtAuthResponse;
    }
}