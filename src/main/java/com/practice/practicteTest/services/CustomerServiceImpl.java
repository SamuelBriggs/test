package com.practice.practicteTest.services;

import com.practice.practicteTest.TYPE;
import com.practice.practicteTest.dtos.requests.AccountCreationRequest;
import com.practice.practicteTest.dtos.requests.SaveCustomerRequest;
import com.practice.practicteTest.models.Account;
import com.practice.practicteTest.models.Customer;
import com.practice.practicteTest.repositories.CustomerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.security.SecureRandom;
import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class CustomerServiceImpl implements CustomerService{

    private CustomerRepository customerRepository;

    private AccountService accountService;

    @Override
    public Customer saveCustomer(SaveCustomerRequest saveCustomerRequest) {
        Customer customer = new Customer();
        customer.setCustomerName(saveCustomerRequest.getCustomerName());
        customer.setCustomerType(saveCustomerRequest.getCustomerType());
        customer.setDateCreated(LocalDateTime.now());
       Customer savedCustomer = customerRepository.save(customer);

       AccountCreationRequest accountCreationRequest = new AccountCreationRequest();
       accountCreationRequest.setCustomerId(String.valueOf(savedCustomer.getCustomerID()));
       accountCreationRequest.setInitialDeposit(saveCustomerRequest.getInitialDeposit());

       accountService.createAccount(accountCreationRequest);

        return customer;
    }

    @Override
    public Customer findCustomerById(Long customerId) {
        return customerRepository.findCustomerByCustomerID(customerId);
    }

    private static Long generateAccountNumber(){
        SecureRandom secureRandom = new SecureRandom();
        // Generate a random number between 1000000000 and 9999999999 (inclusive)
        return 1000000000L + Math.abs(secureRandom.nextLong()) % 9000000000L;
    }

    private static Account createAccountForCustomer(AccountCreationRequest accountCreationRequest, Customer customer){
        Account account = new Account();
        account.setAccountBalance(BigDecimal.ZERO);
        account.setAccountNumber(String.valueOf(generateAccountNumber()));
        account.setDateCreated(LocalDateTime.now());
        account.setCustomerID(customer.getCustomerID());
        return account;
    }

}
