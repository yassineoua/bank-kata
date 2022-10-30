package com.yassineoua.bank.domain.util;

import com.yassineoua.bank.domain.exceptions.InvalidBalanceException;

import java.math.BigDecimal;

public class ValidationUtils {

    public static void shouldBeNotNull(Object o, String message) {
        if (o == null) {
            throw new NullPointerException(message);
        }
    }

    public static void shouldBePositive(Number nb, String message) {
        if (nb.intValue() < 0) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void checkBalance(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new InvalidBalanceException(amount);
        }
    }

    public static boolean isNegative(BigDecimal amount) {
        return BigDecimal.ZERO.compareTo(amount) > 0;
    }
}
