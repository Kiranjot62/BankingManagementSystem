package BankingManagmentSystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class User {
    private final Connection connection;


    public User(Connection connection) {
        this.connection = connection;
    }
    public void register(){
        System.out.println();
        String name = InputHelper.readNonEmptyLine("Enter your Name: ");
        String email = InputHelper.readNonEmptyLine("Enter your Email : ");
        String password = InputHelper.readNonEmptyLine("Enter your Password : ");
        String register_query = "INSERT INTO user(fullName, email, password) VALUES(?,?,?)";
        if(user_exit(email)){
            System.out.println("User Already Exists for this Email Address!!");
            return;
        }
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(register_query);
            preparedStatement.setString(1,name);
            preparedStatement.setString(2,email);
            preparedStatement.setString(3,password);

            int rowsAffected =  preparedStatement.executeUpdate();
            if(rowsAffected > 0){
                System.out.println("rowsAffected"+ rowsAffected);
            }else {
                System.out.println("NOTHING CHANGE");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public  String login(){
        System.out.println();
        String email = InputHelper.readNonEmptyLine("Enter the Email: ");
        String password = InputHelper.readNonEmptyLine("Enter the Password: ");
        String login_query = "SELECT * from user WHERE email = ? AND password = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(login_query);
            preparedStatement.setString(1,email);
            preparedStatement.setString(2,password);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                return email;
            }else {
                return null;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public  boolean user_exit(String email){
        String emailQuery = "SELECT * from user where email =?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(emailQuery);
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
