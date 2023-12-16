package com.practice.practicteTest.Utils;

import com.practice.practicteTest.exceptions.CustomerExistException;
import com.practice.practicteTest.models.Customer;
import com.practice.practicteTest.repositories.CustomerRepository;

import java.math.BigDecimal;
import java.util.regex.Pattern;

public class AppUtils {

    private static CustomerRepository customerRepository;
    public static final BigDecimal CustomerDiscountRate = BigDecimal.valueOf(0.18);

    public static final BigDecimal BusinessDiscountRate = BigDecimal.valueOf(0.27);

    public static final int numberOfYearsRequiredToQualifyForDiscount = 4;

    public static final int amountOfTransactionsPerMonthToQualifyForDiscount = 3;

    public static final BigDecimal discountRateForUserAbove4Years = BigDecimal.valueOf(0.1);

    public static void validateAmount(String amount) {
        BigDecimal figure = new BigDecimal(amount);
        if (figure.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Amount cannot be negative");
        }
    }

    public static void validateCustomerDoesNotExist(String phoneNumberOrEmailAddress) throws CustomerExistException {
        if (phoneNumberOrEmailAddress.matches("\\d{11}")) {
            boolean phoneNumberExist = customerRepository.existsByPhoneNumber(phoneNumberOrEmailAddress);
            if (phoneNumberExist) {
                throw new CustomerExistException(String.format("Customer with phone number %s already exists", phoneNumberOrEmailAddress));
            }
        } else if (isValidEmailAddress(phoneNumberOrEmailAddress)) {
            boolean emailAddressExist = customerRepository.existsByEmailAddress(phoneNumberOrEmailAddress);
            if (emailAddressExist) {
                throw new CustomerExistException(String.format("Customer with email address %s already exists", phoneNumberOrEmailAddress));
            }
        } else {
            throw new IllegalArgumentException("Invalid phone number or email address format");
        }
    }

    private static boolean isValidEmailAddress(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(emailRegex);
        return pattern.matcher(email).matches();
    }
}
