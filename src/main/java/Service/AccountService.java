package Service;

import DAO.AccountDAO;
import Model.Account;

public class AccountService {

    AccountDAO accountDAO;

    /**
     * No-args constructor for a accountService instantiates a plain accountDAO.
     */
    public AccountService(){
        accountDAO = new AccountDAO();
    }

    /**
     * ## 1: Our API should be able to process new User registrations.
     * 
     * - The registration will be successful if and only if the username is not blank, the password is at least 4 characters long, 
     *      and an Account with that username does not already exist. The new account should be persisted to the database.
     * 
     * @param account an object modeling an Account. The account object does not contain an account_id.
     * @return account with generated account_id if successful, null otherwise.
     */
    public Account addNewAccount(Account account){
        //checks used for account creation
        if ((account.getUsername().isBlank()) || (account.getPassword().length() < 4) || (accountDAO.checkAccountExistsByUsername(account.getUsername()))){
            return null;
        }
        return accountDAO.insertAccount(account);
    }
    
    /**
     * ## 2: Our API should be able to process User logins.
     * 
     * - The login will be successful if and only if the username and password provided in the request body JSON match a real account existing on the database. 
     *      If successful, the response body should contain a JSON of the account in the response body, including its account_id.
     * 
     * @param account an object modeling an Account. The account object does not contain an account_id.
     * @return account with account_id if successful, null otherwise.
     */
    public Account loginWithAccount(Account account){
        return accountDAO.loginWithAccount(account);
    }
}
