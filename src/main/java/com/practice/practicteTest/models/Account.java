package com.practice.practicteTest.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@ToString
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long accountID;

    private String accountNumber;

    private Long customerID;

    private BigDecimal accountBalance;

    private LocalDateTime dateCreated;





}
