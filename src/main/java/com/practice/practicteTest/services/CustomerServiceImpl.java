package com.practice.practicteTest.services;

import com.practice.practicteTest.TYPE;
import com.practice.practicteTest.Utils.AppUtils;
import com.practice.practicteTest.Utils.ExceptionUtils;
import com.practice.practicteTest.dtos.requests.AccountCreationRequest;
import com.practice.practicteTest.dtos.requests.SaveCustomerRequest;
import com.practice.practicteTest.dtos.responses.SavedCustomerResponse;
import com.practice.practicteTest.exceptions.CustomerExistException;
import com.practice.practicteTest.exceptions.CustomerNotFoundException;
import com.practice.practicteTest.models.Account;
import com.practice.practicteTest.models.Customer;
import com.practice.practicteTest.repositories.CustomerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

import static com.practice.practicteTest.Utils.AppUtils.validateAmount;
import static com.practice.practicteTest.Utils.AppUtils.validateCustomerDoesNotExist;

@Service
@AllArgsConstructor
public class CustomerServiceImpl implements CustomerService{

    private CustomerRepository customerRepository;
    private AccountService accountService;
//    private AppUtils appUtils;

    /**
     * I commented out customer id field from Save Customer Request, I guess it was an oversight because its not in use
     * I think you should be returning customer account number, so I made the create account return a String
     * Save customer now returns the customer account number after account opening
     * added validations
     * */

    @Override
    public SavedCustomerResponse saveCustomer(SaveCustomerRequest saveCustomerRequest) throws CustomerExistException {
        validateCustomerDoesNotExist(saveCustomerRequest.getPhoneNumberOrEmailAddress());
        validateAmount(saveCustomerRequest.getInitialDeposit());
        Customer customer = new Customer();
        customer.setCustomerName(saveCustomerRequest.getCustomerName());
        customer.setCustomerType(saveCustomerRequest.getCustomerType());
        customer.setDateCreated(LocalDateTime.now());
        Customer savedCustomer = customerRepository.save(customer);

       AccountCreationRequest accountCreationRequest = new AccountCreationRequest();
       accountCreationRequest.setCustomerId(String.valueOf(savedCustomer.getCustomerID()));
       accountCreationRequest.setInitialDeposit(saveCustomerRequest.getInitialDeposit());
       String accountNumber = accountService.createAccount(accountCreationRequest);

        return SavedCustomerResponse.builder().
                customerName(savedCustomer.getCustomerName()).
                CustomerId(savedCustomer.getCustomerID()).
                accountNumber(accountNumber).build();
    }

    @Override
    public SavedCustomerResponse findCustomerById(Long customerId) throws CustomerNotFoundException {
        Optional<Customer> foundCustomer = customerRepository.findCustomerByCustomerID(customerId);
       Customer customer = foundCustomer.orElseThrow(() -> new CustomerNotFoundException(String.format(ExceptionUtils.CUSTOMER_NOT_FOUND, customerId)));
        return SavedCustomerResponse.builder().
                accountNumber(String.valueOf(customer.getCustomerID())).
                customerType(customer.getCustomerType()).dateCreated(customer.getDateCreated()).
                customerName(customer.getCustomerName()).build();
    }



}
