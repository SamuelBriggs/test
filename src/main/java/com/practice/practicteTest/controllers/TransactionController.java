package com.practice.practicteTest.controllers;

import com.practice.practicteTest.dtos.requests.TransferRequest;
import com.practice.practicteTest.exceptions.AccountNotFoundException;
import com.practice.practicteTest.exceptions.CustomerNotFoundException;
import com.practice.practicteTest.services.TransactionService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("api/v1/transaction")
public class TransactionController {

    private TransactionService transactionService;
    @PostMapping("/transfer")

    public ResponseEntity<?> transfer(@RequestBody TransferRequest request){
            try {
               var response =  transactionService.transfer(request);
                return ResponseEntity.ok(response) ;
            } catch (CustomerNotFoundException | AccountNotFoundException e)  {
                return ResponseEntity.badRequest().body(e);
            }
    }
}
