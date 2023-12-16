package com.practice.practicteTest.repositories;

import com.practice.practicteTest.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends CrudRepository<Customer, Long> {
   Optional<Customer> findCustomerByCustomerID (Long customerId);

   boolean existsByPhoneNumber(String phoneNumberOrEmailAddress);

   boolean existsByEmailAddress(String phoneNumberOrEmailAddress);


}
