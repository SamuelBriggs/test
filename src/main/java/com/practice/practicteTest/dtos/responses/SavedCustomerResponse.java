package com.practice.practicteTest.dtos.responses;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.practice.practicteTest.TYPE;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SavedCustomerResponse {
    private Long CustomerId;
    private String customerName;
    private String accountNumber;
    private TYPE customerType;
    private LocalDateTime dateCreated;
}
