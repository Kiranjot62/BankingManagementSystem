package BankingManagmentSystem;

import java.util.Scanner;

public class InputHelper {
    private InputHelper() {
        // utility class, not meant to be instantiated
    }

    public static int readInt(Scanner scanner) {
        while (!scanner.hasNextInt()) {
            System.out.println("Please enter a valid whole number: ");
            scanner.next();
        }
        return scanner.nextInt();
    }

    public static long readLong(Scanner scanner) {
        while (!scanner.hasNextLong()) {
            System.out.println("Please enter a valid number: ");
            scanner.next();
        }
        return scanner.nextLong();
    }

    public static double readPositiveDouble(Scanner scanner) {
        while (true) {
            if (scanner.hasNextDouble()) {
                double value = scanner.nextDouble();
                if (value > 0) {
                    return value;
                }
                System.out.println("Amount must be greater than 0: ");
            } else {
                System.out.println("Please enter a valid amount: ");
                scanner.next();
            }
        }
    }

    public static String readNonEmptyLine(Scanner scanner, String prompt) {
        while (true) {
            System.out.println(prompt);
            String value = scanner.nextLine();
            if (value != null && !value.trim().isEmpty()) {
                return value.trim();
            }
            System.out.println("This field cannot be empty.");
        }
    }
}
