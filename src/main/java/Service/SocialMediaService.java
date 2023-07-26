package Service;

import java.util.List;

import DAO.SocialMediaDAOImpl;
import Model.Account;
import Model.Message;

public class SocialMediaService {
    SocialMediaDAOImpl socialMediaDao;

    //No-args constructor
    public SocialMediaService(){
        socialMediaDao = new SocialMediaDAOImpl(); 
    }

    // constructor with DAO as input
    public SocialMediaService(SocialMediaDAOImpl socialMediaDAO){
        this.socialMediaDao = socialMediaDAO; 
    }

    /*#1 create new account
     * The registration will be successful if and only if the username is not blank, the password is at least 4 characters long,
and an Account with that username does not already exist.
     */
    public Account createAccount(Account account){
        // check for blank username
        if(account.getUsername().equals("")){return null; }
        // check if account already exists. done by querying account id (if exists)
        //check valid password
        if(account.getPassword().length() < 4){return null;}
        return socialMediaDao.createUser(account);
        
    }

    //#2 login a user
    public Account login(Account account){
        return socialMediaDao.loginUser(account); 
    }

    //#3 create new messag
    public Message createNewMessage(Message message){
        if(message.getMessage_text().equals("")){return null; }
        if(message.getMessage_text().length()>=255){return null; }

        return socialMediaDao.createNewMessage(message);

    }


    //#4 retreive all messages 

    public List<Message> getAllMessages(){
        return socialMediaDao.getAllMessages(); 
    }

    //#5 get message by its id
    public Message getMessagesByMessageId(int message_id){
        return socialMediaDao.getMessagesByMessageId(message_id); 
    }

    //#6 delelte message by it's id
    public Message deleteMessageById(int message_id){
        Message message = socialMediaDao.getMessagesByMessageId(message_id); 
        socialMediaDao.deleteMessageByMessageId(message_id);
        return message; 

    }


    // #7 update message by id

    public Message updateMessage(int message_id, Message message){
        // need to do error handling
        // - The update of a message should be successful if and only if the message id already exists and the new
    //  message_text is not blank and is not over 255 characters
        if(message.getMessage_text().length() >=255){return null; }
        if(message.getMessage_text().equals("")){return null; }
        
        socialMediaDao.updateMessageByMessageId(message_id, message);
        
        return socialMediaDao.getMessagesByMessageId(message_id); 
    }

    //#8 get messages by id
    public List<Message> getMessagesByAccountId(int account_id){
        return socialMediaDao.getMessagesByAccountId(account_id); 
    }

}
