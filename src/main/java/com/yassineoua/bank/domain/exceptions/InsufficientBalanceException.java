package com.yassineoua.bank.domain.exceptions;

import java.math.BigDecimal;

public class InsufficientBalanceException extends RuntimeException {

    public InsufficientBalanceException(BigDecimal balance, BigDecimal amount) {
        super(String.format("Account current balance is not available to withdraw. current balance: %s, amount: %s", balance, amount));
    }
}
