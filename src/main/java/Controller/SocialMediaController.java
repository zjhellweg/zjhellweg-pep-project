package Controller;

import io.javalin.Javalin;
import io.javalin.http.Context;
import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.MessageService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.mockito.Answers.valueOf;

import java.util.List;

import org.h2.util.json.JSONObject;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    AccountService accountService;
    MessageService messageService;

    public SocialMediaController(){
        this.accountService = new AccountService();
        this.messageService = new MessageService();
    }


    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.get("example-endpoint", this::exampleHandler);
        app.post("/register",this::registerUser);
        app.post("/login", this::loginUser);
        app.post("/messages",this::createMessage);
        app.get("/messages", this::getAllMessages);
        app.get("/messages/{message_id}",this::getMessageById);
        app.delete("messages/{message_id}", this::deleteMessageById);
        app.patch("/messages/{message_id}",this::updateMessageById);
        app.get("accounts/{account_id}/messages",this::getAllMessagesByUser);

        return app;
    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    
     private void exampleHandler(Context context) {
        context.json("sample text");
    }

    private void registerUser(Context ctx) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(ctx.body(), Account.class);
        Account addedAccount = accountService.addAccount(account);
        if(addedAccount != null){
            ctx.status(200);
        } else {
            ctx.status(400);
        }

    }

    private void loginUser(Context ctx) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(ctx.body(), Account.class);
        Account addedAccount = accountService.userLogin(account);
        if(addedAccount != null){
            ctx.status(200);
        } else {
            ctx.status(401);
        }
        
    }

    private void createMessage(Context ctx) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(ctx.body(), Message.class);
        Message addedMessage = messageService.addMessage(message);
        if(addedMessage != null){
            ctx.json(mapper.writeValueAsString(addedMessage));
            ctx.status(200);
        } else {
            ctx.status(400);
        }
    }

    private void getAllMessages(Context ctx){
        List<Message> messages = messageService.getAllMessages();
        ctx.json(messages);
        ctx.status(200);
    }

    private void getMessageById(Context ctx){
        ctx.status(200);
        int message_id = Integer.parseInt(ctx.pathParam("message_id"));

        Message message = messageService.getMessageByID(message_id);
        if(message!=null){ctx.json(message);}
        ctx.status(200);
    }

    private void deleteMessageById(Context ctx){
        int message_id = Integer.parseInt(ctx.pathParam("message_id"));
        Message message = messageService.deleteMessageByID(message_id);
        if(message != null){
            ctx.json(message);
            ctx.status(200);
        } else {
            ctx.status(200);
        }
    }

    private void updateMessageById(Context ctx) throws JsonMappingException, JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = mapper.readTree(ctx.body());
        String message =  jsonNode.get("message_text").asText();

        int message_id = Integer.parseInt(ctx.pathParam("message_id"));
        Message returnMessage = messageService.updateMessageByID(message_id, message);
        if(returnMessage != null){
            ctx.json(returnMessage);
            ctx.status(200);
        } else {
            ctx.status(400);
        }
    }

    private void getAllMessagesByUser(Context ctx){
        int userId = Integer.parseInt(ctx.pathParam("account_id"));
        List<Message> messages = messageService.allMessagesByUser(userId);
        if(messages != null){ctx.json(messages);}
        ctx.status(200);
    }
    


}