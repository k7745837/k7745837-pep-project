package DAO;

import Model.Account;
import Util.ConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AccountDAO {

    /**
     * Retrieve all accounts from the account table. Does not retrieve passwords for better security.
     * 
     * @return all accounts : ids & usernames ONLY
     */
    List<Account> getAllAccounts(){
        Connection connection = ConnectionUtil.getConnection();
        List<Account> accounts = new ArrayList<Account>();
        
        try {
            //Write SQL logic here
            String sql = "SELECT account_id, username FROM account;";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                Account account = new Account();
                account.setAccount_id(rs.getInt("account_id"));
                account.setUsername(rs.getString("username"));
                accounts.add(account);
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }

        return accounts;
    }
    
    /**
     * TODO: Process new User registrations.
     * 
     * @param account an object modeling an Account. The account object does not contain an account_id.
     * @return
     */
    public Account addNewAccount(Account account){

        return null;
    }
    
    /**
     * TODO: Process User logins.
     * 
     * @param account an object modeling an Account. The account object does not contain an account_id.
     * @return
     */
    public Account loginWithAccount(Account account){

        return null;
    }

}
