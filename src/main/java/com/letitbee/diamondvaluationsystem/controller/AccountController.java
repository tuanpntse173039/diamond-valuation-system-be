package com.letitbee.diamondvaluationsystem.controller;

import com.letitbee.diamondvaluationsystem.payload.AccountDTO;
import com.letitbee.diamondvaluationsystem.payload.AccountResponse;
import com.letitbee.diamondvaluationsystem.service.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/accounts")
public class AccountController {
    private AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    //register Customer
    @PostMapping
    public ResponseEntity<AccountResponse> register(@RequestBody AccountDTO accountDTO){
        AccountResponse response = accountService.register(accountDTO);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updatePassword(@PathVariable(name = "newpassword") String newpassword, @PathVariable(name = "id") long id){
        String response = accountService.updatePassword(newpassword, id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
