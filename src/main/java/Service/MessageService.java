package Service;

import DAO.MessageDAO;
import DAO.AccountDAO;
import Model.Account;
import Model.Message;

import java.util.List;

public class MessageService {
    public MessageDAO messageDAO;
    public AccountDAO accountDAO;

    //Constructor
    public MessageService(){
        messageDAO = new MessageDAO();
        accountDAO = new AccountDAO();
    }

    //service Constructor
    public MessageService(AccountDAO accountDAO, MessageDAO messageDAO){
        this.messageDAO = messageDAO;
        this.accountDAO = accountDAO;
    }

    //Create Message
    public Message addAccount(Message message){
        if(
            message.getMessage_text().length() < 255 &&
            accountDAO.getAccountByAccountNumber(message.getPosted_by()) != null
        ){
            Message returnValue = messageDAO.insertMessage(message);
            if (returnValue != null) {return returnValue;}
        }
        return null;
    }

    public List<Message> getAllMessages(){
        return messageDAO.getAllMessages();
    }

    public Message getMessageByID(int id){
        return messageDAO.getMessageByID(id);
    }

    public Message deleteMessageByID(int id){
        return messageDAO.deleteMessage(id);
    }

    public Message updateMessageByID(int id, String message_text){
        return messageDAO.UpdateMessage(id, message_text);
    }

    public List<Message> allMessagesByUser(int posted_by){
        return messageDAO.getAllMessages(posted_by);
    }

    public Account userLogin(Account account){
        Account verificationAccount = accountDAO.getAccountByUsername(account.getUsername());
        if(verificationAccount.getPassword().equals(account.getPassword())){
            return verificationAccount;
        }
        return null;
    }
}
