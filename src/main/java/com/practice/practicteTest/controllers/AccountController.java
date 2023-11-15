package com.practice.practicteTest.controllers;

import com.practice.practicteTest.dtos.requests.AccountCreationRequest;
import com.practice.practicteTest.services.AccountService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/account")
@AllArgsConstructor
public class AccountController {
    private AccountService accountService;

    @PostMapping("/createAccount")

    public String createAccount(@RequestBody AccountCreationRequest accountCreationRequest){
        accountService.createAccount(accountCreationRequest);
        return "account created";
    }

}
