package com.yassineoua.bank.domain.exceptions;

public class NotFoundCustomerException extends RuntimeException {

    public NotFoundCustomerException(Long customerId) {
        super(String.format("Not found customer with id : %s", customerId.toString()));
    }
}
