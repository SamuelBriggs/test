package com.practice.practicteTest.Utils;

import com.practice.practicteTest.models.Customer;

import java.math.BigDecimal;

public class AppUtils {
    public static final BigDecimal CustomerDiscountRate = BigDecimal.valueOf(0.27);

    public static final BigDecimal BusinessDiscountRate = BigDecimal.valueOf(0.18);

    public static final int numberOfYearsRequiredToQualifyForDiscount = 4;

    public static final int amountOfTransactionsPerMonthToQualifyForDiscount = 3;

    public static final BigDecimal discountRateForUserAbove4Years = BigDecimal.valueOf(0.1);
}
