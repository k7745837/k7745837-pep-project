package DAO;

import Model.Account;
import Util.ConnectionUtil;

import java.sql.*;

public class AccountDAO {

    /**
     * Checks database to see if a username is already in use.
     * 
     * @param username to check for.
     * @return {@code true} if account username is in account table, {@code false} otherwise.
     */
    public boolean checkAccountExistsByUsername(String username){
        Connection connection = ConnectionUtil.getConnection();
        try {
            //Write SQL logic here
            String sql = "SELECT username FROM account WHERE username = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            //write preparedStatement's setString and setInt methods here.
            preparedStatement.setString(1, username);

            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()){
                return true;
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return false;
    }

    /**
     * Checks database to see if an account_id is in use.
     * 
     * @param account_id to check for.
     * @return {@code true} if account account_id is in account table, {@code false} otherwise.
     */
    public boolean checkAccountExistsById(int account_id){
        Connection connection = ConnectionUtil.getConnection();
        try {
            //Write SQL logic here
            String sql = "SELECT username FROM account WHERE account_id = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            //write preparedStatement's setString and setInt methods here.
            preparedStatement.setInt(1, account_id);

            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()){
                return true;
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return false;
    }
    
    /**
     * ## 1: Our API should be able to process new User registrations.
     * 
     * @param account an object modeling an Account. The account object does not contain an account_id.
     * @return account with generated account_id if successful, null otherwise.
     */
    public Account insertAccount(Account account){
        Connection connection = ConnectionUtil.getConnection();
        try {
            //Write SQL logic here.
            String sql = "INSERT INTO account (username, password) VALUES (?, ?);" ;
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            //write preparedStatement's setString and setInt methods here.
            preparedStatement.setString(1, account.getUsername());
            preparedStatement.setString(2, account.getPassword());

            preparedStatement.executeUpdate();
            ResultSet pkeyResultSet = preparedStatement.getGeneratedKeys();
            if (pkeyResultSet.next()){
                int generated_account_id = (int) pkeyResultSet.getLong(1);
                return new Account(generated_account_id, account.getUsername(), account.getPassword());
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }
    
    /**
     * ## 2: Our API should be able to process User logins.
     * 
     * @param account an object modeling an Account. The account object does not contain an account_id.
     * @return account with account_id if successful, null otherwise.
     */
    public Account loginWithAccount(Account account){
        Connection connection = ConnectionUtil.getConnection();
        try {
            //Write SQL logic here
            String sql = "SELECT * FROM account WHERE username = ? AND password = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            //write preparedStatement's setString and setInt methods here.
            preparedStatement.setString(1, account.getUsername());
            preparedStatement.setString(2, account.getPassword());

            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()){
                return new Account(rs.getInt("account_id"), rs.getString("username"), rs.getString("password"));
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

}
