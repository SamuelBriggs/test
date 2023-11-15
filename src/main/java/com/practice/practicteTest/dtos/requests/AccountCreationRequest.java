package com.practice.practicteTest.dtos.requests;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountCreationRequest {
   // private String accountNumber;
    private String customerId;
    private String initialDeposit;

}
