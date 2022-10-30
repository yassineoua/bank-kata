package com.yassineoua.bank.infrastructure.repositories;

import com.yassineoua.bank.domain.model.BankAccount;
import com.yassineoua.bank.domain.model.Customer;
import com.yassineoua.bank.helpers.BankAccountTestHelper;
import com.yassineoua.bank.helpers.CustomerTestHelper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.List;

import static com.yassineoua.bank.helpers.BankAccountTestHelper.*;
import static com.yassineoua.bank.helpers.CustomerTestHelper.*;

public class InMemoryBankAccountRepositoryTest {

    @InjectMocks
    private InMemoryBankAccountRepository inMemoryBankAccountRepository;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindByCustomerId() {
        Customer customer = buildCustomer(CustomerTestHelper.withId(1L), withName("Jack", "mimoun"), withEmail("jack@example.com"));
        BankAccount bankAccount1 = buildAccount(BankAccountTestHelper.withId(1L), withNumber("TEST-ACCOUNT"), withBalance(BigDecimal.valueOf(200)));
        BankAccount bankAccount2 = buildAccount(BankAccountTestHelper.withId(2L), withNumber("TEST2-ACCOUNT"), withBalance(BigDecimal.valueOf(100)));

        bankAccount1.setCustomer(customer);
        bankAccount2.setCustomer(customer);

        inMemoryBankAccountRepository.save(bankAccount1);
        inMemoryBankAccountRepository.save(bankAccount2);

        List<BankAccount> bankAccounts = inMemoryBankAccountRepository.findByCustomerId(1L);
        Assertions.assertNotNull(bankAccounts);
        Assertions.assertEquals(2, bankAccounts.size());
    }
}
