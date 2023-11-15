package com.practice.practicteTest.services;

import com.practice.practicteTest.TYPE;
import com.practice.practicteTest.Utils.AppUtils;
import com.practice.practicteTest.dtos.requests.TransferRequest;
import com.practice.practicteTest.dtos.responses.ApiResponse;
import com.practice.practicteTest.dtos.responses.SavedCustomerResponse;
import com.practice.practicteTest.exceptions.AccountNotFoundException;
import com.practice.practicteTest.exceptions.CustomerNotFoundException;
import com.practice.practicteTest.models.Account;
import com.practice.practicteTest.models.Customer;
import com.practice.practicteTest.models.Transaction;
import com.practice.practicteTest.repositories.TransactionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TransactionServiceImpl implements TransactionService{

    private TransactionRepository transactionRepository;

    private AccountService accountService;

    private CustomerService customerService;



    @Override
    public ApiResponse<?> transfer(TransferRequest transferRequest) throws CustomerNotFoundException, AccountNotFoundException {

      BigDecimal discountIfCustomerOver4years = applyUserDurationDiscount(transferRequest);
      BigDecimal amountAfterUserDurationDiscountIsApplied = BigDecimal.valueOf(Long.parseLong(transferRequest.getAmount())).subtract(discountIfCustomerOver4years);
      BigDecimal discountedRate =  checkCustomerTypeAndApplyAppropriateDiscount(transferRequest, amountAfterUserDurationDiscountIsApplied);
      BigDecimal discountedAmount = BigDecimal.valueOf(Long.parseLong(transferRequest.getAmount())).subtract(discountedRate);

       Transaction transaction = new Transaction();
       transaction.setAccountNumber(transferRequest.getSourceAccount());
       transaction.setTransactionDate(LocalDateTime.now());
       transaction.setDiscountedAmount(discountedAmount);
       transaction.setAmount(new BigDecimal(transferRequest.getAmount()));
       transactionRepository.save(transaction);
        return ApiResponse.builder().message("Transfer Successful").build();
    }

    private BigDecimal applyUserDurationDiscount(TransferRequest transferRequest) throws CustomerNotFoundException, AccountNotFoundException {
        int userDuration = userAccountDuration(transferRequest);
        int numberOfTransactionsInTheCurrentMonth = checkNumberOfCurrentMonthTransactions(transferRequest);
        if (userDuration > AppUtils.numberOfYearsRequiredToQualifyForDiscount && numberOfTransactionsInTheCurrentMonth <= 3) return AppUtils.discountRateForUserAbove4Years.multiply(new BigDecimal(transferRequest.getAmount()));
        return BigDecimal.ZERO;
    }

    @Override
    public List<Transaction> findAllTransactionsByAccountNumber(String accountNumber) {
       return transactionRepository.findAllByAccountNumber(accountNumber);
    }
    private BigDecimal checkCustomerTypeAndApplyAppropriateDiscount(TransferRequest transferRequest, BigDecimal amount) throws AccountNotFoundException, CustomerNotFoundException {
        Account account = accountService.findByAccountNumber(transferRequest.getSourceAccount());
        Long customerID = account.getCustomerID();
        SavedCustomerResponse customer = customerService.findCustomerById(customerID);
        int numberOfCurrentMonthTransactions = checkNumberOfCurrentMonthTransactions(transferRequest);

        if (customer.getCustomerType().equals(TYPE.BUSINESS) & numberOfCurrentMonthTransactions > AppUtils.amountOfTransactionsPerMonthToQualifyForDiscount ){
            return businessCustomerDiscountRate(amount);}

        else if (customer.getCustomerType().equals(TYPE.RETAIL) & numberOfCurrentMonthTransactions > AppUtils.amountOfTransactionsPerMonthToQualifyForDiscount) {
            return retailCustomerDiscountRate(amount);}


        return BigDecimal.ZERO;

        }


    private int checkNumberOfCurrentMonthTransactions(TransferRequest transferRequest){
        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM");
        String formattedDateTime = currentDateTime.format(formatter);
        List<Transaction> transactions = findAllTransactionsByAccountNumber(transferRequest.getSourceAccount());
        List<Transaction> filteredTransactions = transactions.stream()
                .filter(transaction -> transaction.getTransactionDate().format(DateTimeFormatter.ofPattern("yyyy-MM"))
                        .equals(formattedDateTime))
                .toList();
        return  filteredTransactions.size();
    }
    private BigDecimal retailCustomerDiscountRate(BigDecimal amount){
        return AppUtils.CustomerDiscountRate.multiply(amount);
    }

    private BigDecimal businessCustomerDiscountRate(BigDecimal amount){
        return AppUtils.BusinessDiscountRate.multiply(amount);
    }

    private int userAccountDuration(TransferRequest transferRequest) throws AccountNotFoundException, CustomerNotFoundException {
        Account account = accountService.findByAccountNumber(transferRequest.getSourceAccount());
        Long customerID = account.getCustomerID();
        SavedCustomerResponse customer = customerService.findCustomerById(customerID);
        LocalDate timeUserRegistered = customer.getDateCreated().toLocalDate();
        LocalDate currentTime = LocalDateTime.now().toLocalDate();
        Period periodBetweenTheYears = Period.between(timeUserRegistered, currentTime);
        return periodBetweenTheYears.getYears();





    }
}
