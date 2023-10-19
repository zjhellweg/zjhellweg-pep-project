package DAO;

import Util.ConnectionUtil;
import Model.Account;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/*
    create table account (
    account_id int primary key auto_increment,
    username varchar(255) unique,
    password varchar(255)
);
 */

public class AccountDAO {
    public List<String> getAllAccounts(){
        Connection connection = ConnectionUtil.getConnection();
        List<String> accounts = new ArrayList<>();
        try {
            //Write SQL logic here
            String sql = "Select username From Account";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                String account = rs.getString("username");
                accounts.add(account);
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return accounts;
    }

    public Account getAccountByAccountNumber(int accountID){
        Connection connection = ConnectionUtil.getConnection();
        try {
            //Write SQL logic here
            String sql = "Select * From Account where account_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            //write preparedStatement's setInt method here.
            preparedStatement.setInt(1, accountID);

            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                Account account = new Account(rs.getInt("account_ID"),
                        rs.getString("username"),
                        rs.getString("password")
                        );
                return account;
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    public Account getAccountByUsername(String username){
        Connection connection = ConnectionUtil.getConnection();
        try {
            //Write SQL logic here
            String sql = "Select * From Account where username = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            //write preparedStatement's setInt method here.
            preparedStatement.setString(1, username);

            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                Account account = new Account(rs.getInt("account_ID"),
                        rs.getString("username"),
                        rs.getString("password")
                        );
                return account;
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    public Account insertAccount(Account account){
        Connection connection = ConnectionUtil.getConnection();
        try {
            //Write SQL logic here
            String sql = "Insert into Account (username, password) values (?,?)" ;
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            //write preparedStatement's setString and setInt methods here.
            preparedStatement.setString(3, account.getUsername());
            preparedStatement.setString(4, account.getPassword());

            preparedStatement.executeUpdate();
            ResultSet pkeyResultSet = preparedStatement.getGeneratedKeys();
            if(pkeyResultSet.next()){
                int generatedAccountID = (int) pkeyResultSet.getLong(1);
                return new Account(generatedAccountID,account.getUsername(),account.getPassword());
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }
}
