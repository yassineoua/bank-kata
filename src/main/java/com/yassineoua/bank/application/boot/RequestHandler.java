package com.yassineoua.bank.application.boot;

import com.yassineoua.bank.application.context.ApplicationContext;
import com.yassineoua.bank.domain.model.BankAccount;
import com.yassineoua.bank.domain.model.Transaction;
import com.yassineoua.bank.domain.services.BankAccountService;
import com.yassineoua.bank.domain.services.TransactionService;

import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;

public class RequestHandler {
    private final static Long DEFAULT_CUSTOMER_ID = 1L;
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");

    private ConsoleManager consoleManager;
    private BankAccountService bankAccountService;
    private TransactionService transactionService;


    void handleRequests(ApplicationContext applicationContext) {
        this.init(applicationContext);
        this.showMenu();
    }

    private void init(ApplicationContext applicationContext) {
        consoleManager = applicationContext.getComponent(ConsoleManager.class);
        bankAccountService = applicationContext.getComponent(BankAccountService.class);
        transactionService = applicationContext.getComponent(TransactionService.class);
    }

    private void showMenu() {
        consoleManager.breakLines(1);
        consoleManager.print("1. Deposit your money to your account");
        consoleManager.print("2. Make a withdrawal from your account");
        consoleManager.print("3. Show transactions history");
        consoleManager.breakLines(1);
        Integer choice = consoleManager.readInt("==> Your choice : ");
        consoleManager.breakLines(1);
        switch (choice) {
            case 1:
                deposit();
                break;
            case 2:
                withdrawal();
                break;
            case 3:
                showTransactionHistory();
                break;
            default:
                consoleManager.print("Invalid choice");
                showMenu();
        }
    }

    private void deposit() {
        BankAccount bankAccount = pickOneAccount();
        BigDecimal amount = consoleManager.redAmount("Enter amount to save : ");

        try {
            transactionService.executeDeposit(bankAccount.getId(), amount);
            consoleManager.printSuccess("Transaction successfully executed.");
        } catch (Exception e) {
            consoleManager.printError(e.getMessage());
        } finally {
            this.showMenu();
        }
    }

    private BankAccount pickOneAccount() {
        List<BankAccount> bankAccounts = bankAccountService.getBankAccounts(DEFAULT_CUSTOMER_ID);
        showAccountList(bankAccounts);
        Long accountId = consoleManager.readLong("==> Choose one account ID : ");
        BankAccount chosenAccount = bankAccounts.stream().filter(acc -> acc.getId().equals(accountId)).findAny().orElse(null);
        if (Objects.isNull(chosenAccount)) {
            consoleManager.format("No account found for this id : %d%n", accountId);
            consoleManager.print("Please choose of the listed accounts");
            return pickOneAccount();
        }

        return chosenAccount;
    }

    private List<BankAccount> showAccountList(List<BankAccount> bankAccounts) {
        consoleManager.print("************* List of your accounts *************");
        String leftAlignFormat = "| %-2s | %-30s | %-10s |%n";
        consoleManager.print("+--------------------------------------------------+");
        consoleManager.format(leftAlignFormat, "ID", "Account number", "Balance");
        consoleManager.print("+--------------------------------------------------+");

        bankAccounts.forEach(bankAccount -> {
            consoleManager.format(leftAlignFormat, bankAccount.getId(),
                    bankAccount.getAccountNumber(),
                    bankAccount.getBalance().getAmount().floatValue());
        });

        consoleManager.breakLines(1);
        return bankAccounts;
    }

    private void withdrawal() {
        BankAccount bankAccount = pickOneAccount();
        BigDecimal amount = consoleManager.redAmount("Enter amount : ");
        try {
            transactionService.executeWithdraw(bankAccount.getId(), amount);
            consoleManager.printSuccess("Transaction successfully executed.");
        } catch (Exception e) {
            consoleManager.printError(e.getMessage());
        } finally {
            this.showMenu();
        }
    }

    private void showTransactionHistory() {
        BankAccount bankAccount = pickOneAccount();
        List<Transaction> transactions = transactionService.getTransactions(bankAccount.getId());
        String leftAlignFormat = "| %-2s | %-8s | %-20s | %-10s | %-20s | %-10s | %-10s |%n";
        consoleManager.print("+----------------------------------------------------------------------------------------------------+");
        consoleManager.format(leftAlignFormat, "ID", "Type", "Status", "Statement", "Date", "Amount", "Balance");
        consoleManager.print("+----------------------------------------------------------------------------------------------------+");
        IntStream.range(0, transactions.size()).forEach(index -> {
            Transaction transaction = transactions.get(index);
            consoleManager.format(leftAlignFormat, index + 1,
                    transaction.getType().toString(),
                    transaction.getStatus().toString(),
                    transaction.getStatementType().toString(),
                    transaction.getDateTime().format(DATE_FORMATTER),
                    transaction.getAmount().floatValue(),
                    transaction.getBalance().floatValue());
        });

        this.showMenu();
    }
}
