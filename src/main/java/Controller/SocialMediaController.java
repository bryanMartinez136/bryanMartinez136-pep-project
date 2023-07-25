package Controller;

import java.util.List;

import DAO.SocialMediaDAOImpl;
import Model.Message;
import Service.SocialMediaService;
import io.javalin.Javalin;
import io.javalin.http.Context;
import io.javalin.http.Handler;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
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
        app.get("example-endpoint", this::exampleHandler);
        app.get("/messages", this::getAllMessagesHandler);

        return app;
    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void exampleHandler(Context context) {
        context.json("sample text");
    }

    public void createNewMessage(String message){

    }

    public static Handler getAllMessages = ctx -> {
        SocialMediaDAOImpl socialMedia = SocialMediaDAOImpl.instance(); 
        List<Message> allMessages = socialMedia.getAllMessages(); 
        ctx.json(allMessages);  
    }; 


    private void getAllMessagesHandler(Context ctx){
        ctx.json(socialMediaService.getAllMessages()); 
    }

}