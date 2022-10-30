package com.yassineoua.bank.infrastructure.repositories;

import com.yassineoua.bank.domain.model.Transaction;
import com.yassineoua.bank.domain.repositories.TransactionRepository;

import java.util.List;
import java.util.stream.Collectors;

public class InMemoryTransactionRepository extends AbstractInMemoryRepository<Transaction> implements TransactionRepository {

    @Override
    public List<Transaction> findByCustomerId(Long customerId) {
        var dataStore = getDataStore();
        return dataStore.values().stream()
                .filter(transaction -> transaction.getAccount().getCustomer().getId().equals(customerId))
                .collect(Collectors.toList());
    }

    @Override
    public List<Transaction> findByBankAccountId(Long bankAccountId) {
        var dataStore = getDataStore();
        return dataStore.values().stream()
                .filter(transaction -> transaction.getAccount().getId().equals(bankAccountId))
                .collect(Collectors.toList());
    }
}
