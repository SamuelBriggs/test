package com.practice.practicteTest.services;

import com.practice.practicteTest.dtos.requests.AccountCreationRequest;
import com.practice.practicteTest.exceptions.AccountNotFoundException;
import com.practice.practicteTest.models.Account;
import org.springframework.stereotype.Service;


public interface AccountService {
    void createAccount(AccountCreationRequest accountCreationRequest);

    Account findByAccountNumber(String accountNumber) throws AccountNotFoundException;



}
