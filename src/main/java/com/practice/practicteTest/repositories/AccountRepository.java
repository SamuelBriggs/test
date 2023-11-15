package com.practice.practicteTest.repositories;

import com.practice.practicteTest.models.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    Account findByAccountNumber (String accountNumber);
}
