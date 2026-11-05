package BankingManagmentSystem;

import java.sql.*;

public class Account {
    private final Connection connection;

    public Account(Connection connection) {
        this.connection = connection;
    }


    public long open_account(String email) throws SQLException {
        if(!account_exist(email)){

            String query = "INSERT INTO accounts (account_number,full_name,email, balance, security_pin) VALUES(?,?,?,?,?)";
            System.out.println();
            String full_name = InputHelper.readNonEmptyLine("Enter Full Name :  ");
            double balance = InputHelper.readPositiveDouble("Enter initial Amount: ");
            String security_pin = InputHelper.readNonEmptyLine("Enter security Pin: ");
            try {
                long account_number = generate_account();
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setLong(1,account_number);
                preparedStatement.setString(2,full_name);
                preparedStatement.setString(3,email);
                preparedStatement.setDouble(4,balance);
                preparedStatement.setString(5,security_pin);
                int rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected > 0) {
                    return account_number;
                } else {
                    throw new RuntimeException("Account Creation failed!!");
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        throw new RuntimeException("Account Already Exist");

    }
    public long getAccountNumber(String email){
        String query = "SELECT account_number from accounts where email = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) {
                return resultSet.getLong("account_number");
            }else{
                System.out.println("ACCOUNT NUMBER NOT EXITS");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        throw new RuntimeException();
    }

    public long generate_account(){
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT account_number from Accounts ORDER BY account_number DESC LIMIT 1");
            if(resultSet.next()){
                long lastAccount_number = resultSet.getLong("account_number");
                return lastAccount_number+1;
            }else {
                return 10001;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean account_exist(String email){
        String account_query = "SELECT * from accounts where email =?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(account_query);
            preparedStatement.setString(1,email);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                return true;
            }else {
                return false;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
