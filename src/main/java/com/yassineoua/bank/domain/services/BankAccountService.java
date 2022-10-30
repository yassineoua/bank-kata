package com.yassineoua.bank.domain.services;

import com.yassineoua.bank.domain.model.BankAccount;

import java.math.BigDecimal;
import java.util.List;

public interface BankAccountService {

    BankAccount getBankAccount(Long bankAccountId);

    BankAccount addBankAccount(Long customerId, BankAccount bankAccount);

    List<BankAccount> getBankAccounts(Long customerId);

    BankAccount updateBalance(BankAccount bankAccount, BigDecimal amount);

}
