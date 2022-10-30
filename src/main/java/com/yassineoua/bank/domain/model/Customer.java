package com.yassineoua.bank.domain.model;

import com.yassineoua.bank.domain.util.ValidationUtils;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class Customer extends PersistableObject {

    private String firstName;

    private String lastName;

    private String email;

    private String phoneNumber;

    private List<BankAccount> accounts = new ArrayList<>(5);

    public void addAccount(BankAccount bankAccount) {
        ValidationUtils.shouldBeNotNull(bankAccount, "bank account must be not null");

        accounts.add(bankAccount);
        bankAccount.setCustomer(this);
    }

}
