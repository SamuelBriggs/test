package com.practice.practicteTest.services;

import com.practice.practicteTest.Utils.ExceptionUtils;
import com.practice.practicteTest.dtos.requests.AccountCreationRequest;
import com.practice.practicteTest.exceptions.AccountNotFoundException;
import com.practice.practicteTest.models.Account;
import com.practice.practicteTest.repositories.AccountRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AccountServiceImpl implements AccountService{

    private AccountRepository accountRepository;

    @Override
    public void createAccount(AccountCreationRequest accountCreationRequest) {
        Account account = new Account();
        account.setAccountNumber(generateAccount());
        account.setCustomerID(Long.valueOf(accountCreationRequest.getCustomerId()));
        account.setDateCreated(LocalDateTime.now());
        account.setAccountBalance(new BigDecimal(accountCreationRequest.getInitialDeposit()));
        accountRepository.save(account);

    }

    @Override
    public Account findByAccountNumber(String accountNumber) throws AccountNotFoundException {
        Optional<Account> foundAccount = accountRepository.findByAccountNumber(accountNumber);
        return foundAccount.orElseThrow(() -> new AccountNotFoundException(String.format(ExceptionUtils.ACCOUNT_NOT_FOUND, accountNumber)));
    }

    private static String generateAccount(){
        SecureRandom secureRandom = new SecureRandom();
        return String.valueOf(1000000000L + Math.abs(secureRandom.nextLong()) % 9000000000L);

    }


}
