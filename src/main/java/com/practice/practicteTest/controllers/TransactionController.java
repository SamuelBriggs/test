package com.practice.practicteTest.controllers;

import com.practice.practicteTest.dtos.requests.TransferRequest;
import com.practice.practicteTest.services.TransactionService;
import lombok.AllArgsConstructor;
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

    public String transfer(@RequestBody TransferRequest request){
        transactionService.transfer(request);
        return "done";
    }
}
