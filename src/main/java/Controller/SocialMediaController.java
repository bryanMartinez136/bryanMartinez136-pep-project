package Controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import Model.Account;
import Model.Message;
import Service.SocialMediaService;
import io.javalin.Javalin;
import io.javalin.http.Context;

/**
 * You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {

    SocialMediaService socialMediaService; 

    public SocialMediaController(){
        socialMediaService = new SocialMediaService(); 
    }

    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.get("/messages", this::getAllMessagesHandler);
        app.patch("/messages/{message_id}", this::updateMessageByMessageIdHandler);
        app.post( "/register", this::createNewAccount); 
        app.post( "/messages", this::createNewMessageHandler); 
        app.get("/messages/{message_id}", this::getMessageByIdHandler);
        app.delete("/messages/{message_id}", this::deleteMessageByMessageIdHandler); 
        app.post("/login", this::loginToAccount);
        app.get("/accounts/{account_id}/messages", this::getAllMessagesByAccountIdHandler); 
        return app;
    }

    
    /*## 1:Process new User registrations.
    Should be able to create a new Account on the endpoint POST localhost:8080/register. The body will contain a 
    representation of a JSON Account, but will not contain an account_id.

    -successful if and only if :
    a. the username is not blank, 
    b. the password is at least 4 characters long,
    c. Account with that username does not already exist. 
    On succeess, response body should contain
    a. JSON of the Account, including its account_id. 
    b. The response status should be 200 OK, which is the default. 
    The new accountshould be persisted to the database, if unsuccessful, the response status should be 400. (Client error)
    */
    private void createNewAccount(Context ctx) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper(); 
        Account account = mapper.readValue(ctx.body(), Account.class); 
        Account newAccount = socialMediaService.createAccount(account); 

        if(newAccount == null){
            ctx.status(400); 
        }
        else{
            ctx.json(mapper.writeValueAsString(newAccount)); 
        }

    }

    /*## 2: Our API should be able to process User logins.

    Should be able to verify my login on the endpoint POST localhost:8080/login. 
    The request body will contain:
    a. JSON representation of an Account, not containing an account_id. 

    The login will be successful if and only if:
    a. the username and password provided in the request body JSON match a real account existing on the database. 
    If successful, the response body should contain:
    a JSON of the account in the response body, including its account_id. The response status should be 200 OK.
    - Otherwise, the response status should be 401. (Unauthorized)
    */
    private void loginToAccount(Context ctx) throws JsonMappingException, JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper(); 
        Account account = mapper.readValue(ctx.body(), Account.class); 
        Account loginAccount = socialMediaService.login(account); 

        if(loginAccount == null){
            ctx.status(401); 
        }else {
            ctx.json(mapper.writeValueAsString(loginAccount)); 
        }

    }
    
    /*## 3: Our API should be able to process the creation of new messages.

    Should be able to submit a new post on the endpoint POST localhost:8080/messages. 
    The request body will contain:
    a. JSON representation of a message, which should be persisted to the database, but will not contain a message_id.

    - The creation of the message will be successful if and only if:
    a. the message_text is not blank, is under 255 characters
    b. posted_by refers to a real, existing user. 

    -If successful, the response body should contain a JSON of the message including its message_id. 
    The response status should be 200, which is the default. The new message should be persisted to the database.
        
    - Otherwise, the response status should be 400. (Client error)
    */
    private void createNewMessageHandler(Context ctx) throws JsonMappingException, JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(ctx.body(), Message.class); 

        Message createdMessage = socialMediaService.createNewMessage(message); 
        if(createdMessage == null){
            ctx.status(400); 
        }
        else{
            ctx.json(mapper.writeValueAsString(createdMessage));
        }

    }    
    
    /*## 4: Our API should be able to retrieve all messages.

    As a user, I should be able to submit a GET request on the endpoint GET localhost:8080/messages.

    - The response body should contain a JSON representation of a
    list containing all messages retrieved from the database. 
    It is expected for the list to simply be empty if there are no messages. 
    The response status should always be 200, which is the default.
    */
    private void getAllMessagesHandler(Context ctx){
        ctx.json(socialMediaService.getAllMessages()); 
    }

    /* ## 5: Our API should be able to retrieve a message by its ID.
    Should be able to submit a GET request on the endpoint GET localhost:8080/messages/{message_id}.
    - The response body should contain a JSON representation of the message identified by the message_id. 
    It is expected for the response body to simply be empty if there is no such message. 
    The response status should always be 200, which is the default. 
    */
    private void getMessageByIdHandler(Context ctx) throws JsonMappingException, JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper(); 
        // Message message = mapper.readValue(ctx.body(), Message.class); 
        int message_id = Integer.parseInt(ctx.pathParam("message_id")); 
        
       
        Message retreivedMessage = socialMediaService.getMessagesByMessageId(message_id);  
        System.out.println(retreivedMessage);
        if(retreivedMessage == null){
            ctx.status(200); 
        }else{
            ctx.json(mapper.writeValueAsString(retreivedMessage)); 
        }
    }
    
    /*## 6: Our API should be able to delete a message identified by a message ID.
    
    As a User, I should be able to submit a DELETE request on the endpoint DELETE localhost:8080/messages/{message_id}.
    
    - The deletion of an existing message should remove an existing message from the database. 
    If the message existed, the response body should contain the now-deleted message. 
    The response status should be 200, which is the default.
    
    - If the message did not exist, the response status should be 200, but the response body should be empty. 
    This is because the DELETE verb is intended to be idempotent, ie,
    multiple calls to the DELETE endpoint should respond with the same type of response.
     */

     private void deleteMessageByMessageIdHandler(Context ctx) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper(); 
        int message_id = Integer.parseInt(ctx.pathParam("message_id")); 
        
        Message deletedMessage = socialMediaService.deleteMessageById(message_id); 
        if(deletedMessage == null){
            ctx.status(200); 
        }else{
            ctx.json(mapper.writeValueAsString(deletedMessage)); 
        }
     }
    
    /*## 7: Our API should be able to update a message text identified by a message ID.
    
    Should be able to submit a PATCH request on the endpoint PATCH localhost:8080/messages/{message_id}. 
    The request body should contain a new message_text values to replace the message identified by message_id. 
    The request body can not be guaranteed to contain any other information.
    
    - The update of a message should be successful if and only if
    a. the message id already exists 
    b. the new message_text is not blank and is not over 255 characters. 

    If the update is successful, the response body 
     should contain the full updated message
     and the response status should be 200, which is the default. 

     The message existing on the database should have the updated message_text.

    - Otherwise, the response status should be 400. (Client error)
      */

      private void updateMessageByMessageIdHandler(Context ctx) throws JsonMappingException, JsonProcessingException{
        //convert java object to json 
        ObjectMapper mapper = new ObjectMapper(); 
        Message message = mapper.readValue(ctx.body(), Message.class); 
        int message_id = Integer.parseInt(ctx.pathParam("message_id")); 
        Message updatedMessage = socialMediaService.updateMessage(message_id, message); 
        System.out.println(updatedMessage);
        // System.out.println(message.getMessage_text());
        if(updatedMessage == null){
            ctx.status(400); 
        }else{
            ctx.json(mapper.writeValueAsString(updatedMessage)); 
        }

      }
      
      /*## 8: Our API should be able to retrieve all messages written by a particular user.
      * Should be able to submit a GET request on the endpoint GET localhost:8080/accounts/{account_id}/messages.
      
      - The response body contains a JSON representation of a list of all messages posted by a user from the database. 
      It is expected for the list to simply be empty if there are no messages. 
      The response status should always be 200, which is the default.
       */
      private void getAllMessagesByAccountIdHandler(Context ctx){
        int account_id = Integer.parseInt(ctx.pathParam("account_id")); 
        System.out.println(account_id);
        ctx.json(socialMediaService.getMessagesByAccountId(account_id)); 
      }
}
