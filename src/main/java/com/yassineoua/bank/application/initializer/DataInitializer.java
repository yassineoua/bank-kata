package com.yassineoua.bank.application.initializer;

import com.yassineoua.bank.application.context.ApplicationContext;
import com.yassineoua.bank.domain.model.BankAccount;
import com.yassineoua.bank.domain.model.Customer;
import com.yassineoua.bank.domain.repositories.BankAccountRepository;
import com.yassineoua.bank.domain.repositories.CustomerRepository;

import java.math.BigDecimal;

public class DataInitializer implements ApplicationInitializer {

    @Override
    public void init(ApplicationContext applicationContext) {
        Customer customer = new Customer();
        customer.setFirstName("Jack");
        customer.setLastName("Mimoun");
        customer.setEmail("jacl@example.com");

        BankAccount bankAccount = new BankAccount();
        bankAccount.setCustomer(customer);
        bankAccount.setAccountNumber("FR2410096000408429832496Q72");
        bankAccount.setBalance(BigDecimal.valueOf(2500));

        BankAccount bankAccount2 = new BankAccount();
        bankAccount2.setCustomer(customer);
        bankAccount2.setAccountNumber("FR7912739000408343158165G85");
        bankAccount2.setBalance(BigDecimal.valueOf(3000));

        customer.addAccount(bankAccount);
        customer.addAccount(bankAccount2);

        CustomerRepository customerRepository = applicationContext.getComponent(CustomerRepository.class);
        BankAccountRepository bankAccountRepository = applicationContext.getComponent(BankAccountRepository.class);

        customerRepository.save(customer);
        bankAccountRepository.save(bankAccount);
        bankAccountRepository.save(bankAccount2);
    }

}
