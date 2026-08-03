package BankingManagmentSystem;

import java.util.Scanner;

public class BankingApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        UserService userService = new UserService(scanner);
        AccountService accountService = new AccountService();
        AccountManager accountManager = new AccountManager(accountService, scanner);

        while (true) {
            System.out.println();
            System.out.println("*** WELCOME TO BANKING SYSTEM ***");
            System.out.println();
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            System.out.println("Enter your choice: ");
            int choice1 = InputHelper.readInt(scanner);
            scanner.nextLine(); // consume leftover newline

            switch (choice1) {
                case 1:
                    userService.register();
                    break;

                case 2:
                    String email = userService.login();
                    if (email == null) {
                        System.out.println("Incorrect Email or Password!");
                        break;
                    }

                    System.out.println();
                    System.out.println("User Logged In!");

                    if (!accountService.accountExists(email)) {
                        System.out.println();
                        System.out.println("1. Open a new Bank Account");
                        System.out.println("2. Exit");
                        int openChoice = InputHelper.readInt(scanner);
                        if (openChoice == 1) {
                            long newAccountNumber = accountService.openAccount(email);
                            System.out.println("Account Created Successfully");
                            System.out.println("Your Account Number is: " + newAccountNumber);
                        } else {
                            break;
                        }
                    }

                    long accountNumber = accountService.getAccountNumber(email);
                    int choice2 = 0;
                    while (choice2 != 5) {
                        System.out.println();
                        System.out.println("1. Debit Money");
                        System.out.println("2. Credit Money");
                        System.out.println("3. Transfer Money");
                        System.out.println("4. Check Balance");
                        System.out.println("5. Log Out");
                        System.out.println("Enter your choice: ");
                        choice2 = InputHelper.readInt(scanner);

                        switch (choice2) {
                            case 1:
                                accountManager.debitMoney(accountNumber);
                                break;
                            case 2:
                                accountManager.creditMoney(accountNumber);
                                break;
                            case 3:
                                accountManager.transferMoney(accountNumber);
                                break;
                            case 4:
                                accountManager.getBalance(accountNumber);
                                break;
                            case 5:
                                System.out.println("Logging out...");
                                break;
                            default:
                                System.out.println("Enter Valid Choice!");
                                break;
                        }
                    }
                    break;

                case 3:
                    System.out.println("THANK YOU FOR USING BANKING SYSTEM!!!");
                    System.out.println("Exiting System!");
                    scanner.close();
                    return;

                default:
                    System.out.println("Enter Valid Choice");
                    break;
            }
        }
    }
}
