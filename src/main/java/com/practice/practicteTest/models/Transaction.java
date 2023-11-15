package com.practice.practicteTest.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.bytecode.enhance.spi.EnhancementInfo;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Setter
@Getter
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long transactionId;

    private String accountNumber;

    private BigDecimal amount;

    private BigDecimal discountedAmount;

    private BigDecimal rate;

    private LocalDateTime transactionDate;

}
