package com.practice.practicteTest.dtos.requests;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransferRequest {
   private String sourceAccount;
   private String destinationAccount;
   private String amount;
}
