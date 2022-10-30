package com.yassineoua.bank.helpers;

import com.yassineoua.bank.domain.model.BankAccount;
import com.yassineoua.bank.domain.model.Transaction;
import com.yassineoua.bank.domain.model.TransactionStatus;
import com.yassineoua.bank.domain.model.TransactionType;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.function.Consumer;

public class TransactionTestHelper {

    public static Consumer<Transaction> withId(Long id) {
        return (customer) -> customer.setId(id);
    }


    public static Consumer<Transaction> withStatus(TransactionStatus status) {
        return (transaction) -> transaction.setStatus(status);
    }

    public static Consumer<Transaction> withType(TransactionType type) {
        return (transaction) -> transaction.setType(type);
    }

    public static Consumer<Transaction> withAmount(BigDecimal amount) {
        return (transaction) -> transaction.setAmount(amount);
    }

    public static Consumer<Transaction> withAccount(BankAccount account) {
        return (transaction) -> transaction.setAccount(account);
    }

    @SafeVarargs
    public static Transaction buildTransaction(Consumer<Transaction>... setters) {
        Transaction transaction = new Transaction();
        Arrays.stream(setters).forEach(setter -> setter.accept(transaction));
        return transaction;
    }
}
