package Service;

import DAO.AccountDAO;
import Model.Account;

public class AccountService {
    public AccountDAO accountDAO;

    //Constructor
    public AccountService(){
        accountDAO = new AccountDAO();
    }

    //service Constructor
    public AccountService(AccountDAO accountDAO){
        this.accountDAO = accountDAO;
    }

    //Create Account
    public Account addAccount(Account account){
        
        if(account.getPassword().length() >= 4 && account.getUsername().length() > 0){
            if(accountDAO.getAccountByUsername(account.getUsername()) == null){
                Account returnValue = accountDAO.insertAccount(account);
                return returnValue;
            }
        }              
        return null;
        
    }

    public Account userLogin(Account account) {
        if (accountDAO.getAccountByUsername(account.getUsername()) == null) {
            return null;
        }
        Account verificationAccount = accountDAO.getAccountByUsername(account.getUsername());
        if (verificationAccount.getPassword().equals(account.getPassword())) {
            return verificationAccount;
        }
        return null;
    }
}
