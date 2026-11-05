package BankingManagmentSystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class AccountManager {
    private final Connection connection;

    public AccountManager(Connection con) {
        this.connection = con;
    }


    public void debitMoney(long accountNumber) {
        double amount = InputHelper.readPositiveDouble("Enter Amount: ");
        String security_pin =
                InputHelper.readNonEmptyLine("Enter Security Pin: ");

        String checkQuery =
                "SELECT balance FROM accounts " +
                        "WHERE account_number = ? AND security_pin = ?";

        String debitQuery =
                "UPDATE accounts SET balance = balance - ? " +
                        "WHERE account_number = ?";

        try {
            connection.setAutoCommit(false);

            PreparedStatement preparedStatement =
                    connection.prepareStatement(checkQuery);

            preparedStatement.setLong(1, accountNumber);
            preparedStatement.setString(2, security_pin);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {

                double current_balance =
                        resultSet.getDouble("balance");

                if (amount <= current_balance) {

                    PreparedStatement ps1 =
                            connection.prepareStatement(debitQuery);

                    ps1.setDouble(1, amount);
                    ps1.setLong(2, accountNumber);

                    int rowsAffected = ps1.executeUpdate();

                    if (rowsAffected > 0) {

                        connection.commit();
                        System.out.println(
                                "Rs. " + amount + " debited successfully"
                        );

                    } else {

                        connection.rollback();
                        System.out.println("Transaction Failed.");
                    }

                } else {

                    System.out.println("Insufficient Balance.");
                }

            } else {

                System.out.println("Invalid Account Number or Security Pin.");
            }

            connection.setAutoCommit(true);
        } catch (SQLException e) {

            try {
                connection.rollback();
                connection.setAutoCommit(true);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }

            throw new RuntimeException(e);
        }


    }

    public void creditMoney(long accountNumber) {

        double amount =
                InputHelper.readPositiveDouble("Enter Amount: ");

        String security_pin =
                InputHelper.readNonEmptyLine("Enter Security Pin: ");

        String checkQuery =
                "SELECT balance FROM accounts " +
                        "WHERE account_number = ? AND security_pin = ?";

        String creditQuery =
                "UPDATE accounts SET balance = balance + ? " +
                        "WHERE account_number = ?";

        try {

            connection.setAutoCommit(false);

            PreparedStatement preparedStatement =
                    connection.prepareStatement(checkQuery);

            preparedStatement.setLong(1, accountNumber);
            preparedStatement.setString(2, security_pin);

            ResultSet resultSet =
                    preparedStatement.executeQuery();

            if (resultSet.next()) {

                PreparedStatement ps1 =
                        connection.prepareStatement(creditQuery);

                ps1.setDouble(1, amount);
                ps1.setLong(2, accountNumber);

                int rowsAffected = ps1.executeUpdate();

                if (rowsAffected > 0) {

                    connection.commit();

                    System.out.println(
                            "Rs. " + amount + " credited successfully"
                    );

                } else {

                    connection.rollback();
                    System.out.println("Transaction Failed.");
                }

            } else {

                System.out.println("Invalid Account Number or Security Pin.");
            }

            connection.setAutoCommit(true);

        } catch (SQLException e) {

            try {
                connection.rollback();
                connection.setAutoCommit(true);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }

            throw new RuntimeException(e);
        }
    }

    public void transferMoney(long senderAccountNum) throws SQLException {
        long receiverAccountNum =
                InputHelper.readLong("Enter Receiver Account Number: ");

        double amount =
                InputHelper.readPositiveDouble("Enter Amount to Transfer: ");

        String security_pin =
                InputHelper.readNonEmptyLine("Enter Security Pin: ");

        String senderQuery =
                "SELECT balance FROM accounts " +
                        "WHERE account_number = ? AND security_pin = ?";

        String receiverQuery =
                "SELECT account_number FROM accounts " +
                        "WHERE account_number = ?";

        String debitQuery =
                "UPDATE accounts SET balance = balance - ? " +
                        "WHERE account_number = ?";

        String creditQuery =
                "UPDATE accounts SET balance = balance + ? " +
                        "WHERE account_number = ?";

        try {

            connection.setAutoCommit(false);

            // 1. Check sender
            PreparedStatement senderPs =
                    connection.prepareStatement(senderQuery);

            senderPs.setLong(1, senderAccountNum);
            senderPs.setString(2, security_pin);

            ResultSet senderRs =
                    senderPs.executeQuery();

            if (!senderRs.next()) {
                System.out.println("Invalid Security Pin.");
                connection.rollback();
                connection.setAutoCommit(true);
                return;
            }

            double senderBalance =
                    senderRs.getDouble("balance");

            // 2. Check balance
            if (amount > senderBalance) {
                System.out.println("Insufficient Balance.");
                connection.rollback();
                connection.setAutoCommit(true);
                return;
            }

            // 3. Check receiver
            PreparedStatement receiverPs =
                    connection.prepareStatement(receiverQuery);

            receiverPs.setLong(1, receiverAccountNum);

            ResultSet receiverRs =
                    receiverPs.executeQuery();

            if (!receiverRs.next()) {
                System.out.println("Receiver Account Does Not Exist.");
                connection.rollback();
                connection.setAutoCommit(true);
                return;
            }

            // 4. Debit sender
            PreparedStatement debitPs =
                    connection.prepareStatement(debitQuery);

            debitPs.setDouble(1, amount);
            debitPs.setLong(2, senderAccountNum);

            int rowsAffected1 =
                    debitPs.executeUpdate();

            // 5. Credit receiver
            PreparedStatement creditPs =
                    connection.prepareStatement(creditQuery);

            creditPs.setDouble(1, amount);
            creditPs.setLong(2, receiverAccountNum);

            int rowsAffected2 =
                    creditPs.executeUpdate();

            // 6. Commit both operations
            if (rowsAffected1 > 0 && rowsAffected2 > 0) {

                connection.commit();

                System.out.println("Transaction Successful!");
                System.out.println(
                        "Rs. " + amount +
                                " Transferred Successfully"
                );

            } else {

                connection.rollback();
                System.out.println("Transaction Failed.");
            }

            connection.setAutoCommit(true);

        } catch (SQLException e) {

            try {
                connection.rollback();
                connection.setAutoCommit(true);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }

            throw new RuntimeException(e);
        }

    }

    public void getBalance(long accountNumber) {
        String security_pin = InputHelper.readNonEmptyLine("Enter Security Pin : ");
        try{
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT balance FROM Accounts WHERE account_number = ? AND security_pin = ?");
            preparedStatement.setLong(1, accountNumber);
            preparedStatement.setString(2, security_pin);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                double balance = resultSet.getDouble("balance");
                System.out.println("Balance: "+balance);
            }else{
                System.out.println("Invalid Pin!");
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}
