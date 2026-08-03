package BankingManagmentSystem;

import java.util.HashMap;
import java.util.Map;

public class AccountService {
    private final Map<Long, Account> accountsByNumber = new HashMap<>();
    private final Map<String, Long> accountNumberByEmail = new HashMap<>();
    private long nextAccountNumber = 1000000001L;

    public boolean accountExists(String email) {
        return accountNumberByEmail.containsKey(email);
    }

    public long openAccount(String email) {
        long accountNumber = nextAccountNumber++;
        Account account = new Account(accountNumber, email, 0.0);
        accountsByNumber.put(accountNumber, account);
        accountNumberByEmail.put(email, accountNumber);
        return accountNumber;
    }

    public long getAccountNumber(String email) {
        return accountNumberByEmail.get(email);
    }

    public Account getAccount(long accountNumber) {
        return accountsByNumber.get(accountNumber);
    }
}
