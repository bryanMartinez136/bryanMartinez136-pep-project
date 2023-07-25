package DAO;

import java.util.List;

import Model.Message;

public interface SocialMediaDao {
    // define CRUD operations 
    
    public void createUser(); 
    public void createNewMessage(Message message); 
    public List<Message> getAllMessages(); 
    public List<Message> getAllMessagesByMessageId(int message_id); 
    public void deleteMessageById(int message_id); 
    public void updateMessageById(int message_id); 
    public List<Message> getAllMessagesByAccountId(int account_id); 

 



 
}
