package com.yassineoua.bank.domain.model;

import com.yassineoua.bank.domain.util.ValidationUtils;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@Data
@EqualsAndHashCode(callSuper = true)
public class BankAccount extends PersistableObject {

    private String accountNumber;

    private Money balance;

    private Customer customer;

    public void setBalance(BigDecimal balance) {
        ValidationUtils.checkBalance(balance);
        this.balance = new Money(balance);
    }
}
