package com.yassineoua.bank.domain.repositories;

import com.yassineoua.bank.domain.model.Transaction;

import java.util.List;

public interface TransactionRepository {

    Transaction save(Transaction transaction);

    List<Transaction> findByCustomerId(Long customerId);

    List<Transaction> findByBankAccountId(Long bankAccountId);
}
