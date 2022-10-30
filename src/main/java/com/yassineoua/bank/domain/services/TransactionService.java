package com.yassineoua.bank.domain.services;

import com.yassineoua.bank.domain.model.Transaction;

import java.math.BigDecimal;
import java.util.List;

public interface TransactionService {

    void executeWithdraw(Long bankAccountId, BigDecimal amount);

    void executeDeposit(Long bankAccountId, BigDecimal amount);

    List<Transaction> getTransactions(Long bankAccountId);
}
