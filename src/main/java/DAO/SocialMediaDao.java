package DAO;

import java.util.List;

import Model.Account;
import Model.Message;

public interface SocialMediaDao {
    // define CRUD operations 
    
    public Account createUser(Account account); 
    public void createNewMessage(Message message); 
    public List<Message> getAllMessages(); 
    public Message getMessagesByMessageId(int message_id); 
    public void deleteMessageByMessageId(int message_id); 
    public void updateMessageByMessageId(int message_id, Message message); 
    public List<Message> getMessagesByAccountId(int account_id); 

 



 
}
