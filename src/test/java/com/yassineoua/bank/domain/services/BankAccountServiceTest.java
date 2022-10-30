package com.yassineoua.bank.domain.services;

import com.yassineoua.bank.domain.exceptions.InvalidBalanceException;
import com.yassineoua.bank.domain.exceptions.NotFoundBankAccountException;
import com.yassineoua.bank.domain.exceptions.NotFoundCustomerException;
import com.yassineoua.bank.helpers.BankAccountTestHelper;
import com.yassineoua.bank.helpers.CustomerTestHelper;
import com.yassineoua.bank.domain.model.BankAccount;
import com.yassineoua.bank.domain.model.Customer;
import com.yassineoua.bank.domain.repositories.BankAccountRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static com.yassineoua.bank.helpers.BankAccountTestHelper.*;
import static com.yassineoua.bank.helpers.CustomerTestHelper.buildCustomer;
import static com.yassineoua.bank.helpers.CustomerTestHelper.withName;

public class BankAccountServiceTest {

    @Mock
    private BankAccountRepository bankAccountRepository;

    @Mock
    private CustomerService customerService;

    @InjectMocks
    private BankAccountServiceImpl bankAccountService;


    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetBankAccount() {
        BankAccount bankAccount = buildAccount(BankAccountTestHelper.withId(1L), withNumber("TEST-ACCOUNT"), withBalance(BigDecimal.valueOf(200)));

        Mockito.when(bankAccountRepository.findById(bankAccount.getId())).thenReturn(bankAccount);

        BankAccount retrievedBankAccount = bankAccountService.getBankAccount(1L);

        Assertions.assertNotNull(retrievedBankAccount);
        Assertions.assertEquals(retrievedBankAccount.getId(), bankAccount.getId());
    }

    @Test
    void testGetBankAccountWhenNotFound() {
        Mockito.when(bankAccountRepository.findById(1L)).thenReturn(null);

        Assertions.assertThrows(NotFoundBankAccountException.class, () -> {
            bankAccountService.getBankAccount(1L);
        });
    }


    @Test
    void testAddBankAccountToCustomer() {
        BankAccount bankAccount = buildAccount(BankAccountTestHelper.withId(1L), withNumber("TEST-ACCOUNT"), withBalance(BigDecimal.valueOf(200)));
        Customer customer = buildCustomer(CustomerTestHelper.withId(1L), withName("Jack", "mimoun"));

        Mockito.when(customerService.getCustomer(customer.getId())).thenReturn(customer);

        bankAccountService.addBankAccount(1L, bankAccount);

        Mockito.verify(customerService, Mockito.times(1)).saveCustomer(customer);
        Assertions.assertEquals(1, customer.getAccounts().size());
    }

    @Test
    void testAddBankAccountNotFoundCustomer() {
        BankAccount bankAccount = buildAccount(BankAccountTestHelper.withId(1L), withNumber("TEST-ACCOUNT"), withBalance(BigDecimal.valueOf(200)));
        Mockito.when(customerService.getCustomer(1L)).thenThrow(new NotFoundCustomerException(1L));

        Assertions.assertThrows(NotFoundCustomerException.class, () -> {
            bankAccountService.addBankAccount(1L, bankAccount);
        });
    }

    @Test
    void testGetAccountsByCustomerId() {
        BankAccount bankAccount1 = buildAccount(BankAccountTestHelper.withId(1L), withNumber("TEST-ACCOUNT"), withBalance(BigDecimal.valueOf(200)));
        BankAccount bankAccount2 = buildAccount(BankAccountTestHelper.withId(2L), withNumber("TEST2-ACCOUNT"), withBalance(BigDecimal.valueOf(450)));

        Mockito.when(bankAccountRepository.findByCustomerId(1L)).thenReturn(Arrays.asList(bankAccount1, bankAccount2));

        List<BankAccount> bankAccounts = bankAccountService.getBankAccounts(1L);

        Assertions.assertEquals(2, bankAccounts.size());
    }

    @Test
    void testUpdateBalanceWithNegativeValue() {
        BankAccount bankAccount = buildAccount(BankAccountTestHelper.withId(1L), withNumber("TEST-ACCOUNT"), withBalance(BigDecimal.valueOf(200)));
        Assertions.assertThrows(InvalidBalanceException.class, () -> {
            bankAccountService.updateBalance(bankAccount, BigDecimal.valueOf(-25));
        });
    }
}
