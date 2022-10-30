package com.yassineoua.bank.domain.repositories;

import com.yassineoua.bank.domain.model.BankAccount;

import java.util.List;

public interface BankAccountRepository {

    BankAccount save(BankAccount bankAccount);

    List<BankAccount> findByCustomerId(Long customerId);

    BankAccount findById(Long bankAccountId);
}
