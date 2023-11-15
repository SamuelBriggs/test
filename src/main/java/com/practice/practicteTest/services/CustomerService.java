package com.practice.practicteTest.services;

import com.practice.practicteTest.dtos.requests.SaveCustomerRequest;
import com.practice.practicteTest.models.Customer;

public interface CustomerService {
    Customer saveCustomer(SaveCustomerRequest saveCustomerRequest);

    Customer findCustomerById (Long customerId);
}
