package com.yassineoua.bank.domain.services;

import com.yassineoua.bank.domain.exceptions.NotFoundBankAccountException;
import com.yassineoua.bank.domain.model.BankAccount;
import com.yassineoua.bank.domain.model.Customer;
import com.yassineoua.bank.domain.repositories.BankAccountRepository;
import com.yassineoua.bank.domain.util.ValidationUtils;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@AllArgsConstructor
public class BankAccountServiceImpl implements BankAccountService {

    private BankAccountRepository bankAccountRepository;
    private CustomerService customerService;

    @Override
    public BankAccount getBankAccount(Long bankAccountId) {
        ValidationUtils.shouldBeNotNull(bankAccountId, "bankAccountId must be not be null");
        BankAccount bankAccount = bankAccountRepository.findById(bankAccountId);
        if (Objects.isNull(bankAccount)) {
            throw new NotFoundBankAccountException(bankAccountId);
        }
        return bankAccount;
    }

    @Override
    public BankAccount addBankAccount(Long customerId, BankAccount bankAccount) {
        ValidationUtils.shouldBeNotNull(bankAccount, "bankAccount must be not be null");
        ValidationUtils.shouldBeNotNull(bankAccount.getBalance(), "balance must be not be null");
        ValidationUtils.shouldBePositive(bankAccount.getBalance().getAmount(), "balance must not be negative");

        Customer customer = customerService.getCustomer(customerId);

        bankAccount.setCustomer(customer);
        customer.addAccount(bankAccount);

        bankAccount = bankAccountRepository.save(bankAccount);
        customerService.saveCustomer(customer);

        return bankAccount;
    }

    @Override
    public List<BankAccount> getBankAccounts(Long customerId) {
        return bankAccountRepository.findByCustomerId(customerId);
    }

    @Override
    public BankAccount updateBalance(BankAccount bankAccount, BigDecimal amount) {
        ValidationUtils.checkBalance(amount);
        bankAccount.setBalance(amount);
        return bankAccountRepository.save(bankAccount);
    }
}
