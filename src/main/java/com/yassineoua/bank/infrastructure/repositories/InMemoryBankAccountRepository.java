package com.yassineoua.bank.infrastructure.repositories;

import com.yassineoua.bank.domain.model.BankAccount;
import com.yassineoua.bank.domain.repositories.BankAccountRepository;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class InMemoryBankAccountRepository extends AbstractInMemoryRepository<BankAccount> implements BankAccountRepository {

    @Override
    public List<BankAccount> findByCustomerId(Long customerId) {
        var dataStore = getDataStore();
        return dataStore.values().stream().filter(bankAccount -> Objects.nonNull(bankAccount.getCustomer()))
                .filter(bankAccount -> bankAccount.getCustomer().getId().equals(customerId))
                .collect(Collectors.toList());
    }

}
