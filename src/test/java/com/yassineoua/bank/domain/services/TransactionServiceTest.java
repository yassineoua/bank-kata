package com.yassineoua.bank.domain.services;

import com.yassineoua.bank.domain.exceptions.InsufficientBalanceException;
import com.yassineoua.bank.domain.model.*;
import com.yassineoua.bank.domain.repositories.TransactionRepository;
import com.yassineoua.bank.helpers.BankAccountTestHelper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.math.BigDecimal;

import static com.yassineoua.bank.helpers.BankAccountTestHelper.*;

public class TransactionServiceTest {

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private BankAccountService bankAccountService;

    @InjectMocks
    private TransactionServiceImpl transactionService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testWithdraw() {
        BankAccount bankAccount = buildAccount(BankAccountTestHelper.withId(1L), withNumber("TEST-ACCOUNT"), withBalance(BigDecimal.valueOf(200)));

        Mockito.when(bankAccountService.getBankAccount(1L)).thenReturn(bankAccount);
        Mockito.when(bankAccountService.updateBalance(Mockito.any(), Mockito.any())).thenReturn(bankAccount);

        transactionService.executeWithdraw(1L, BigDecimal.valueOf(150));

        ArgumentCaptor<Transaction> transactionCaptor = ArgumentCaptor.forClass(Transaction.class);
        Mockito.verify(transactionRepository, Mockito.times(1)).save(transactionCaptor.capture());

        Transaction transaction = transactionCaptor.getValue();
        Assertions.assertNotNull(transaction);
        Assertions.assertEquals(TransactionStatus.SUCCESS, transaction.getStatus());
        Assertions.assertEquals(TransactionType.WITHDRAW, transaction.getType());
        Assertions.assertEquals(StatementType.EXPENSE, transaction.getStatementType());
        Assertions.assertEquals(bankAccount, transaction.getAccount());
        Assertions.assertEquals(150, transaction.getAmount().longValue());
    }

    @Test
    void testWithdrawInsufficientBalanceException() {
        BankAccount bankAccount = buildAccount(BankAccountTestHelper.withId(1L), withNumber("TEST-ACCOUNT"), withBalance(BigDecimal.valueOf(200)));

        Mockito.when(bankAccountService.getBankAccount(1L)).thenReturn(bankAccount);
        Mockito.when(bankAccountService.updateBalance(Mockito.any(), Mockito.any())).thenReturn(bankAccount);

        Assertions.assertThrows(InsufficientBalanceException.class, () -> {
            transactionService.executeWithdraw(1L, BigDecimal.valueOf(210));

            ArgumentCaptor<Transaction> transactionCaptor = ArgumentCaptor.forClass(Transaction.class);
            Mockito.verify(transactionRepository, Mockito.times(1)).save(transactionCaptor.capture());

            Transaction transaction = transactionCaptor.getValue();
            Assertions.assertNotNull(transaction);
            Assertions.assertEquals(TransactionStatus.INSUFFICIENT_BALANCE, transaction.getStatus());
        });
    }

    @Test
    void testWithdrawRuntimeException() {
        BankAccount bankAccount = buildAccount(BankAccountTestHelper.withId(1L), withNumber("TEST-ACCOUNT"), withBalance(BigDecimal.valueOf(200)));

        Mockito.when(bankAccountService.getBankAccount(1L)).thenReturn(bankAccount);
        Mockito.doThrow(new RuntimeException()).when(bankAccountService).updateBalance(Mockito.any(), Mockito.any());

        Assertions.assertThrows(RuntimeException.class, () -> {
            transactionService.executeWithdraw(1L, BigDecimal.valueOf(210));

            ArgumentCaptor<Transaction> transactionCaptor = ArgumentCaptor.forClass(Transaction.class);
            Mockito.verify(transactionRepository, Mockito.times(1)).save(transactionCaptor.capture());

            Transaction transaction = transactionCaptor.getValue();
            Assertions.assertNotNull(transaction);
            Assertions.assertEquals(TransactionStatus.FAIL, transaction.getStatus());
        });
    }

    @Test
    void testDeposit() {
        BankAccount bankAccount = buildAccount(BankAccountTestHelper.withId(1L), withNumber("TEST-ACCOUNT"), withBalance(BigDecimal.valueOf(200)));

        Mockito.when(bankAccountService.getBankAccount(1L)).thenReturn(bankAccount);
        Mockito.when(bankAccountService.updateBalance(Mockito.any(), Mockito.any())).thenReturn(bankAccount);

        transactionService.executeDeposit(1L, BigDecimal.valueOf(600));

        ArgumentCaptor<Transaction> transactionCaptor = ArgumentCaptor.forClass(Transaction.class);
        Mockito.verify(transactionRepository, Mockito.times(1)).save(transactionCaptor.capture());

        Transaction transaction = transactionCaptor.getValue();
        Assertions.assertNotNull(transaction);
        Assertions.assertEquals(TransactionStatus.SUCCESS, transaction.getStatus());
        Assertions.assertEquals(TransactionType.DEPOSIT, transaction.getType());
        Assertions.assertEquals(StatementType.INCOME, transaction.getStatementType());
        Assertions.assertEquals(bankAccount, transaction.getAccount());
        Assertions.assertEquals(600, transaction.getAmount().longValue());
    }

    @Test
    void testDepositRuntimeException() {
        BankAccount bankAccount = buildAccount(BankAccountTestHelper.withId(1L), withNumber("TEST-ACCOUNT"), withBalance(BigDecimal.valueOf(200)));

        Mockito.when(bankAccountService.getBankAccount(1L)).thenReturn(bankAccount);
        Mockito.doThrow(new RuntimeException()).when(bankAccountService).updateBalance(Mockito.any(), Mockito.any());

        Assertions.assertThrows(RuntimeException.class, () -> {
            transactionService.executeDeposit(1L, BigDecimal.valueOf(210));

            ArgumentCaptor<Transaction> transactionCaptor = ArgumentCaptor.forClass(Transaction.class);
            Mockito.verify(transactionRepository, Mockito.times(1)).save(transactionCaptor.capture());

            Transaction transaction = transactionCaptor.getValue();
            Assertions.assertNotNull(transaction);
            Assertions.assertEquals(TransactionStatus.FAIL, transaction.getStatus());
        });
    }
}
