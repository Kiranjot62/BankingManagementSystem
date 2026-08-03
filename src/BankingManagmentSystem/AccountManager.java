package BankingManagmentSystem;

import java.util.Scanner;

public class AccountManager {
    private final AccountService accountService;
    private final Scanner scanner;

    public AccountManager(AccountService accountService, Scanner scanner) {
        this.accountService = accountService;
        this.scanner = scanner;
    }

    public void debitMoney(long accountNumber) {
        Account account = accountService.getAccount(accountNumber);
        System.out.println("Enter amount to debit (withdraw): ");
        double amount = InputHelper.readPositiveDouble(scanner);

        if (amount > account.getBalance()) {
            System.out.println("Insufficient balance!");
            return;
        }
        account.withdraw(amount);
        System.out.println("Debit successful. New balance: " + account.getBalance());
    }

    public void creditMoney(long accountNumber) {
        Account account = accountService.getAccount(accountNumber);
        System.out.println("Enter amount to credit (deposit): ");
        double amount = InputHelper.readPositiveDouble(scanner);

        account.deposit(amount);
        System.out.println("Credit successful. New balance: " + account.getBalance());
    }

    public void transferMoney(long fromAccountNumber) {
        Account fromAccount = accountService.getAccount(fromAccountNumber);

        System.out.println("Enter recipient's account number: ");
        long toAccountNumber = InputHelper.readLong(scanner);

        if (toAccountNumber == fromAccountNumber) {
            System.out.println("You cannot transfer money to your own account.");
            return;
        }

        Account toAccount = accountService.getAccount(toAccountNumber);
        if (toAccount == null) {
            System.out.println("Recipient account not found.");
            return;
        }

        System.out.println("Enter amount to transfer: ");
        double amount = InputHelper.readPositiveDouble(scanner);

        if (amount > fromAccount.getBalance()) {
            System.out.println("Insufficient balance!");
            return;
        }

        fromAccount.withdraw(amount);
        toAccount.deposit(amount);
        System.out.println("Transfer successful. New balance: " + fromAccount.getBalance());
    }

    public void getBalance(long accountNumber) {
        Account account = accountService.getAccount(accountNumber);
        System.out.println("Your current balance is: " + account.getBalance());
    }
}
