package com.yassineoua.bank.domain.services;

import com.yassineoua.bank.domain.exceptions.InsufficientBalanceException;
import com.yassineoua.bank.domain.model.*;
import com.yassineoua.bank.domain.repositories.TransactionRepository;
import com.yassineoua.bank.domain.util.ValidationUtils;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@AllArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private TransactionRepository transactionRepository;

    private BankAccountService bankAccountService;

    @Override
    public void executeWithdraw(Long bankAccountId, BigDecimal amount) {
        ValidationUtils.shouldBeNotNull(bankAccountId, "bankAccountId must be not null");
        ValidationUtils.shouldBePositive(amount, "amount must be positive");

        BankAccount bankAccount = bankAccountService.getBankAccount(bankAccountId);

        Transaction transaction = Transaction.builder()
                .type(TransactionType.WITHDRAW)
                .statementType(StatementType.EXPENSE)
                .account(bankAccount)
                .amount(amount)
                .build();

        try {
            doWithdraw(transaction, bankAccount, amount);
        } catch (InsufficientBalanceException e) {
            markTransactionAsFailed(transaction, TransactionStatus.INSUFFICIENT_BALANCE);
            throw e;

        } catch (Exception e) {
            markTransactionAsFailed(transaction, TransactionStatus.FAIL);
            throw e;
        } finally {
            transactionRepository.save(transaction);
        }

    }


    private void doWithdraw(Transaction transaction, BankAccount bankAccount, BigDecimal amount) {
        checkBalance(bankAccount, amount);
        bankAccount = bankAccountService.updateBalance(bankAccount, bankAccount.getBalance().getAmount().subtract(amount));
        transaction.setStatus(TransactionStatus.SUCCESS);
        transaction.setBalance(bankAccount.getBalance().getAmount());
    }

    private void checkBalance(BankAccount bankAccount, BigDecimal amount) {
        if (ValidationUtils.isNegative(bankAccount.getBalance().getAmount().subtract(amount))) {
            throw new InsufficientBalanceException(bankAccount.getBalance().getAmount(), amount);
        }
    }

    @Override
    public void executeDeposit(Long bankAccountId, BigDecimal amount) {
        ValidationUtils.shouldBeNotNull(bankAccountId, "bankAccountId must be not null");
        ValidationUtils.shouldBePositive(amount, "amount must be positive");

        BankAccount bankAccount = bankAccountService.getBankAccount(bankAccountId);

        Transaction transaction = Transaction.builder()
                .type(TransactionType.DEPOSIT)
                .statementType(StatementType.INCOME)
                .account(bankAccount)
                .amount(amount)
                .build();

        try {
            doDeposit(transaction, bankAccount, amount);
        } catch (Exception e) {
            markTransactionAsFailed(transaction, TransactionStatus.FAIL);
            throw e;
        } finally {
            transactionRepository.save(transaction);
        }

    }

    @Override
    public List<Transaction> getTransactions(Long bankAccountId) {
        ValidationUtils.shouldBeNotNull(bankAccountId, "bankAccountId must be not null");
        return transactionRepository.findByBankAccountId(bankAccountId);
    }

    private void doDeposit(Transaction transaction, BankAccount bankAccount, BigDecimal amount) {
        bankAccountService.updateBalance(bankAccount, bankAccount.getBalance().getAmount().add(amount));
        transaction.setStatus(TransactionStatus.SUCCESS);
        transaction.setBalance(bankAccount.getBalance().getAmount());
    }

    private void markTransactionAsFailed(Transaction transaction, TransactionStatus status) {
        transaction.setStatus(status);
        transaction.setBalance(transaction.getAccount().getBalance().getAmount());
    }

}
