package com.practice.practicteTest.services;

import com.practice.practicteTest.dtos.requests.TransferRequest;
import com.practice.practicteTest.dtos.responses.ApiResponse;
import com.practice.practicteTest.dtos.responses.TransferSuccessResponse;
import com.practice.practicteTest.exceptions.AccountNotFoundException;
import com.practice.practicteTest.exceptions.CustomerNotFoundException;
import com.practice.practicteTest.models.Transaction;

import java.util.List;

public interface TransactionService {
    ApiResponse<?> transfer(TransferRequest transferRequest) throws CustomerNotFoundException, AccountNotFoundException;

    List<Transaction> findAllTransactionsByAccountNumber(String accountNumber);



}
