package com.yassineoua.bank.application.initializer;

import com.yassineoua.bank.application.boot.ConsoleManager;
import com.yassineoua.bank.application.context.ApplicationContext;
import com.yassineoua.bank.application.context.DefaultApplicationContext;
import com.yassineoua.bank.domain.repositories.BankAccountRepository;
import com.yassineoua.bank.domain.repositories.CustomerRepository;
import com.yassineoua.bank.domain.repositories.TransactionRepository;
import com.yassineoua.bank.domain.services.*;
import com.yassineoua.bank.infrastructure.repositories.InMemoryBankAccountRepository;
import com.yassineoua.bank.infrastructure.repositories.InMemoryCustomerRepository;
import com.yassineoua.bank.infrastructure.repositories.InMemoryTransactionRepository;

public class ContextInitializer implements ApplicationInitializer {

    public void init(ApplicationContext applicationContext) {
        DefaultApplicationContext context = (DefaultApplicationContext) applicationContext;
        registerCoreComponents(context);
        registerRepositories(context);
        registerServices(context);
    }

    private void registerCoreComponents(DefaultApplicationContext context) {
        context.registerComponent(ConsoleManager.class, new ConsoleManager());
    }

    private void registerRepositories(DefaultApplicationContext context) {
        context.registerComponent(BankAccountRepository.class, new InMemoryBankAccountRepository());
        context.registerComponent(CustomerRepository.class, new InMemoryCustomerRepository());
        context.registerComponent(TransactionRepository.class, new InMemoryTransactionRepository());
    }

    private void registerServices(DefaultApplicationContext context) {
        CustomerServiceImpl customerService = new CustomerServiceImpl(context.getComponent(CustomerRepository.class));
        BankAccountServiceImpl bankAccountService = new BankAccountServiceImpl(context.getComponent(BankAccountRepository.class), customerService);
        TransactionServiceImpl transactionService = new TransactionServiceImpl(context.getComponent(TransactionRepository.class), bankAccountService);

        context.registerComponent(CustomerService.class, customerService);
        context.registerComponent(BankAccountService.class, bankAccountService);
        context.registerComponent(TransactionService.class, transactionService);
    }

}
