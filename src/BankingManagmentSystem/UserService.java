package BankingManagmentSystem;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class UserService {
    private final Map<String, User> map = new HashMap<>();
    private final Scanner scanner;

    public UserService(Scanner scanner) {
        this.scanner = scanner;
    }

    public void register() {
        System.out.println();
        System.out.println("--- Register ---");
        String name = InputHelper.readNonEmptyLine(scanner, "Enter your name: ");
        String email = InputHelper.readNonEmptyLine(scanner, "Enter your email: ");

        if (map.containsKey(email)) {
            System.out.println("An account with this email already exists. Please login instead.");
            return;
        }

        String password = InputHelper.readNonEmptyLine(scanner, "Enter your password: ");
        map.put(email, new User(name, email, password));
        System.out.println("Registration successful! You can now log in.");
    }

    /**
     * @return the logged-in user's email, or null if login failed.
     */
    public String login() {
        System.out.println();
        System.out.println("--- Login ---");
        String email = InputHelper.readNonEmptyLine(scanner, "Enter your email: ");
        String password = InputHelper.readNonEmptyLine(scanner, "Enter your password: ");

        User user = map.get(email);
        if (user != null && user.getPassword().equals(password)) {
            return email;
        }
        return null;
    }
}
