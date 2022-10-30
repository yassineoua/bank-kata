package com.yassineoua.bank.domain.exceptions;

public class NotFoundBankAccountException extends RuntimeException {

    public NotFoundBankAccountException(Long bankAccountId) {
        super(String.format("Not found bank account with id : %s", bankAccountId.toString()));
    }
}
