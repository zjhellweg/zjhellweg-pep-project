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
    public Message addMessage(Message message){
        if(
            message.getMessage_text().length() < 255 &&
            message.getMessage_text().length() > 0 &&
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

    public Message getMessageByID(int id) {
        Message returnValue = messageDAO.getMessageByID(id);

        return returnValue;

    }

    public Message deleteMessageByID(int id){
        Message returnValue = messageDAO.getMessageByID(id);
        if(returnValue != null){
            messageDAO.deleteMessage(id);
        }
        
        if(messageDAO.getMessageByID(id) == null){
            return returnValue;
        }
        return null;
    }

    public Message updateMessageByID(int id, String message_text) {
        if (message_text.length() < 255 && message_text.length() > 0) {
                messageDAO.UpdateMessage(id, message_text);
                return messageDAO.getMessageByID(id);
        }
        return null;
        
    }

    public List<Message> allMessagesByUser(int posted_by){
        return messageDAO.getAllMessages(posted_by);
    }
}
