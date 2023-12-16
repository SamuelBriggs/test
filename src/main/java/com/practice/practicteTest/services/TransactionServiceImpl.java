package com.practice.practicteTest.services;

import com.practice.practicteTest.TYPE;
import com.practice.practicteTest.Utils.AppUtils;
import com.practice.practicteTest.dtos.requests.TransferRequest;
import com.practice.practicteTest.dtos.responses.ApiResponse;
import com.practice.practicteTest.dtos.responses.SavedCustomerResponse;
import com.practice.practicteTest.exceptions.AccountNotFoundException;
import com.practice.practicteTest.exceptions.CustomerNotFoundException;
import com.practice.practicteTest.models.Account;
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

import static com.practice.practicteTest.Utils.AppUtils.validateAmount;

@Service
@AllArgsConstructor
public class TransactionServiceImpl implements TransactionService{

    private TransactionRepository transactionRepository;

    private AccountService accountService;

    private CustomerService customerService;


    /*
    * I have problem with your variable/method namings here, if I didn't see the question it will hard to tell what your code is doing
    * I changed method applyUserDurationDiscount to applyDiscountForCustomerOver4Years
    * Added amount validation for negative figure
    * */
    @Override
    public ApiResponse<?> transfer(TransferRequest transferRequest) throws CustomerNotFoundException, AccountNotFoundException {
      validateAmount(transferRequest.getAmount());
      BigDecimal discountIfCustomerOver4years = applyDiscountForCustomerOver4Years(transferRequest);
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

    /*
    * Changed variable userDuration to customerAccountDurationInYears
    * */
    private BigDecimal applyDiscountForCustomerOver4Years(TransferRequest transferRequest) throws CustomerNotFoundException, AccountNotFoundException {
        int customerAccountDurationInYears = userAccountDuration(transferRequest);
        int numberOfTransactionsInTheCurrentMonth = checkNumberOfCurrentMonthTransactions(transferRequest);
        if (customerAccountDurationInYears > AppUtils.numberOfYearsRequiredToQualifyForDiscount && numberOfTransactionsInTheCurrentMonth <= 3) return AppUtils.discountRateForUserAbove4Years.multiply(new BigDecimal(transferRequest.getAmount()));
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
            return retailCustomerDiscountRate(amount);
        }

        return BigDecimal.ZERO;
        }


    private int checkNumberOfCurrentMonthTransactions(TransferRequest transferRequest){
        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM");
        String formattedDateTime = currentDateTime.format(formatter);
        List<Transaction> transactions = findAllTransactionsByAccountNumber(transferRequest.getSourceAccount()); // naming conventions
        List<Transaction> filteredTransactions = transactions.stream()
                .filter(transaction -> transaction.getTransactionDate().format(DateTimeFormatter.ofPattern("yyyy-MM"))
                        .equals(formattedDateTime))
                .toList();
        return  filteredTransactions.size();
    }

    /*
     * You flipped the discount rate in App Utils, I changed it thou, you had 0.18 for business and 0.27 for retail
     * */
    private BigDecimal retailCustomerDiscountRate(BigDecimal amount){
        return AppUtils.CustomerDiscountRate.multiply(amount);
    }

    /*
     * You flipped the discount rate in App Utils, I changed it thou, you had 0.18 for business and 0.27 for retail
     * */
    private BigDecimal businessCustomerDiscountRate(BigDecimal amount){
        return AppUtils.BusinessDiscountRate.multiply(amount);
    }

    /*
    * Naming convention is not good enough, so I changed it
    * */
    private int userAccountDuration(TransferRequest transferRequest) throws AccountNotFoundException, CustomerNotFoundException {
        Account account = accountService.findByAccountNumber(transferRequest.getSourceAccount());
        Long customerID = account.getCustomerID();
        SavedCustomerResponse customer = customerService.findCustomerById(customerID);
        LocalDate dateCustomerRegistered = customer.getDateCreated().toLocalDate();
        LocalDate currentDate = LocalDateTime.now().toLocalDate();
        Period periodBetweenTheYears = Period.between(dateCustomerRegistered, currentDate);
        return periodBetweenTheYears.getYears();





    }
}
