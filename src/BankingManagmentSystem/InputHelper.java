package BankingManagmentSystem;

import java.util.Scanner;

public class InputHelper {
    static Scanner scanner = new Scanner(System.in);

    private InputHelper() {
        // utility class, not meant to be instantiated
    }

    public static int readInt(String prompt) {

        while (true) {
            System.out.print(prompt);

            try {
                return Integer.parseInt(scanner.nextLine().trim());

            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid whole number.");
            }
        }
    }

    public static long readLong(String prompt) {

        while (true) {
            System.out.print(prompt);

            try {
                return Long.parseLong(scanner.nextLine().trim());

            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }

    public static double readPositiveDouble(String prompt) {

        while (true) {
            System.out.print(prompt);

            try {
                double value = Double.parseDouble(scanner.nextLine().trim());

                if (value > 0) {
                    return value;
                }

                System.out.println("Amount must be greater than 0.");

            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid amount.");
            }
        }
    }

    public static String readNonEmptyLine(String prompt) {

        while (true) {
            System.out.print(prompt);

            String value = scanner.nextLine().trim();

            if (!value.isEmpty()) {
                return value;
            }

            System.out.println("This field cannot be empty.");
        }
    }
}
