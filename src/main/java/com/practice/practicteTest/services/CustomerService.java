package com.practice.practicteTest.services;

import com.practice.practicteTest.dtos.requests.SaveCustomerRequest;
import com.practice.practicteTest.dtos.responses.SavedCustomerResponse;
import com.practice.practicteTest.exceptions.CustomerNotFoundException;
import com.practice.practicteTest.models.Customer;

public interface CustomerService {
    SavedCustomerResponse saveCustomer(SaveCustomerRequest saveCustomerRequest);

    SavedCustomerResponse findCustomerById (Long customerId) throws CustomerNotFoundException;
}
