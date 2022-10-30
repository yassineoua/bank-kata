package com.yassineoua.bank.helpers;

import com.yassineoua.bank.domain.model.BankAccount;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.function.Consumer;

public class BankAccountTestHelper {

    public static Consumer<BankAccount> withId(Long id) {
        return (customer) -> customer.setId(id);
    }

    public static Consumer<BankAccount> withNumber(String number) {
        return (customer) -> customer.setAccountNumber(number);
    }

    public static Consumer<BankAccount> withBalance(BigDecimal balanceAmount) {
        return (customer) -> customer.setBalance(balanceAmount);
    }

    @SafeVarargs
    public static BankAccount buildAccount(Consumer<BankAccount>... setters) {
        BankAccount bankAccount = new BankAccount();
        Arrays.stream(setters).forEach(setter -> setter.accept(bankAccount));
        return bankAccount;
    }
}
