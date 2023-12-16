package com.practice.practicteTest.dtos.requests;

import com.practice.practicteTest.TYPE;
import com.practice.practicteTest.models.Account;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
@Setter
@Getter
@ToString
public class SaveCustomerRequest {

//    private Long customerID;
    private String customerName;
    private TYPE customerType;
    private String initialDeposit;
    private String phoneNumberOrEmailAddress;


}
