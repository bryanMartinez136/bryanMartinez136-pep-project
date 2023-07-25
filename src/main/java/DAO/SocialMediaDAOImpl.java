package DAO;

import Model.Message;
import Util.ConnectionUtil;

import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class SocialMediaDAOImpl implements SocialMediaDao {
    private static SocialMediaDAOImpl socialDAO = null; 
    public static SocialMediaDAOImpl instance(){
        if(socialDAO == null){
            socialDAO = new SocialMediaDAOImpl(); 
        }
        return socialDAO; 
    }
    
    @Override
    public void createNewMessage(Message message) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'createNewMessage'");
    }


    @Override
    public List<Message> getAllMessages(){
        Connection connection = ConnectionUtil.getConnection(); 
        List<Message> messages = new ArrayList<>(); 

        try {

            String sql = "select * from message"; 
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery(); 
            while (rs.next()) {
                messages.add(new Message(rs.getInt("message_id"), rs.getInt("posted_by"), rs.getString("message_text"), rs.getLong("time_posted_epoch"))); 
            }

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } 
        
        return new ArrayList<Message>(); 
    }

    @Override
    public void createUser() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'createUser'");
    }

    @Override
    public List<Message> getAllMessagesByMessageId(int message_id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAllMessagesByMessageId'");
    }

    @Override
    public void deleteMessageById(int message_id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteMessageById'");
    }

    @Override
    public void updateMessageById(int message_id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateMessageById'");
    }

    @Override
    public List<Message> getAllMessagesByAccountId(int account_id) {
        // TODO Auto-generated method stub
        Connection connetcion = ConnectionUtil.getConnection(); 
        try {
            
            String sql = "select * from messages where posted_by = ? "; 
            PreparedStatement preparedStatement = connetcion.prepareStatement(sql);
            preparedStatement.setInt(1, account_id); 

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } 
        
        throw new UnsupportedOperationException("Unimplemented method 'getAllMessagesByAccountId'");
    }

}
