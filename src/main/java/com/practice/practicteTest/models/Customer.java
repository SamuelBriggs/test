package com.practice.practicteTest.models;

import com.practice.practicteTest.TYPE;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long customerID;
    private String customerName;
    private TYPE customerType;
    @OneToMany(mappedBy = "customerID", cascade = CascadeType.ALL)
    private List<Account> accounts;
    private LocalDateTime dateCreated;
}
