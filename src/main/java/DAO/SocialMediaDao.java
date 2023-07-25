package DAO;

import java.util.List;

import Model.Account;
import Model.Message;

public interface SocialMediaDao {
    // define CRUD operations 
    
    public void createUser(Account account); 
    public void createNewMessage(Message message); 
    public List<Message> getAllMessages(); 
    public List<Message> getAllMessagesByMessageId(int message_id); 
    public void deleteMessageByMessageId(int message_id); 
    public void updateMessageByMessageId(int message_id, Message message); 
    public List<Message> getAllMessagesByAccountId(int account_id); 

 



 
}
