package com.yassineoua.bank.domain.exceptions;

import java.math.BigDecimal;

public class InvalidBalanceException extends RuntimeException {

    public InvalidBalanceException(BigDecimal amount) {
        super(String.format("invalid balance : <%s>", amount.toString()));
    }
}
