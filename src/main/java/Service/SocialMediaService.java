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

    /*
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


    public void createNewMessage(){
        socialMediaDao.createNewMessage(null);
    }


    // retreive all messages 

    public List<Message> getAllMessages(){
        return socialMediaDao.getAllMessages(); 
    }


    // #7 update message by id

    public Message updateMessage(int message_id, Message message){
        socialMediaDao.updateMessageByMessageId(message_id, message);
        // need to do error handling
        return socialMediaDao.getMessagesByMessageId(message_id); 
    }

}
