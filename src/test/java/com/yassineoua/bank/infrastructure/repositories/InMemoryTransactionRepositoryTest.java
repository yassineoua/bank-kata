package com.yassineoua.bank.infrastructure.repositories;

import com.yassineoua.bank.domain.model.*;
import com.yassineoua.bank.helpers.BankAccountTestHelper;
import com.yassineoua.bank.helpers.CustomerTestHelper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.IntStream;

import static com.yassineoua.bank.helpers.BankAccountTestHelper.buildAccount;
import static com.yassineoua.bank.helpers.BankAccountTestHelper.withBalance;
import static com.yassineoua.bank.helpers.CustomerTestHelper.*;
import static com.yassineoua.bank.helpers.TransactionTestHelper.*;

public class InMemoryTransactionRepositoryTest {

    @InjectMocks
    private InMemoryTransactionRepository inMemoryTransactionRepository;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindByCustomerId() {
        BankAccount bankAccount = buildAccount(BankAccountTestHelper.withId(1L), withBalance(BigDecimal.valueOf(500)));
        Customer customer = buildCustomer(CustomerTestHelper.withId(1L), withName("Jack", "mimoun"), withEmail("jack@example.com"));
        bankAccount.setCustomer(customer);

        IntStream.range(0, 10).forEach(value -> {
            Transaction transaction = buildTransaction(
                    withType(TransactionType.DEPOSIT),
                    withAccount(bankAccount),
                    withStatus(TransactionStatus.SUCCESS),
                    withAmount(BigDecimal.valueOf(value + 150)));

            inMemoryTransactionRepository.save(transaction);
        });

        List<Transaction> transactions = inMemoryTransactionRepository.findByCustomerId(1L);


        Assertions.assertNotNull(transactions);
        Assertions.assertEquals(10, transactions.size());
    }
}
