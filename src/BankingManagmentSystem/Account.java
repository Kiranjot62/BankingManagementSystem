package BankingManagmentSystem;

public class Account {
    private final long accountNumber;
    private final String email;
    private double balance;

    public Account(long accountNumber, String email, double balance) {
        this.accountNumber = accountNumber;
        this.email = email;
        this.balance = balance;
    }

    public long getAccountNumber() {
        return accountNumber;
    }

    public String getEmail() {
        return email;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        balance += amount;
    }

    public void withdraw(double amount) {
        balance -= amount;
    }
}
