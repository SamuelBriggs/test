package com.practice.practicteTest;

import com.practice.practicteTest.dtos.requests.SaveCustomerRequest;
import com.practice.practicteTest.models.Customer;
import com.practice.practicteTest.repositories.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class Customer_Test {

    @Autowired
    private CustomerRepository customerService;

    @Test
    public void test_That_Customer_Can_Register(){
        SaveCustomerRequest saveCustomerRequest = new SaveCustomerRequest();
        saveCustomerRequest.setCustomerName("Alawode Samuel");
        saveCustomerRequest.setCustomerType(TYPE.RETAIL);
        Customer customer = new Customer();
        customer.setCustomerName("Alawode Tolulope");
        customerService.save(customer);
        System.out.println(customer);
    }

    


}
