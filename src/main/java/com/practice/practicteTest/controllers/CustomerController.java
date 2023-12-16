package com.practice.practicteTest.controllers;

import com.practice.practicteTest.dtos.requests.SaveCustomerRequest;
import com.practice.practicteTest.dtos.responses.ApiResponse;
import com.practice.practicteTest.exceptions.CustomerExistException;
import com.practice.practicteTest.models.Customer;
import com.practice.practicteTest.services.CustomerService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/customer")
@AllArgsConstructor
public class CustomerController {

    private CustomerService customerService;

    @PostMapping("/register")
    public ResponseEntity<?> registerCustomer(@RequestBody SaveCustomerRequest customerRequest) throws CustomerExistException {
            var response = customerService.saveCustomer(customerRequest);
            return ResponseEntity.ok(ApiResponse.builder().message("Customer Registered Successfully").data(response).build());

    }

}
