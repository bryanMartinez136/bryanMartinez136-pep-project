package DAO;

import Model.Account;
import Model.Message;
import Util.ConnectionUtil;

import java.util.List;

import org.h2.result.ResultTarget;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * 
 * Table named 'message'
 * Has values similar to the Message class:
 * message_id, which is of type int,(primary key auto increment)
 * posted_by, int
 * message_text, varchar(255)
 *   time_posted_epoch bigint,
 * 
 * foreign key (posted_by) references  account(account_id)
 */

public class SocialMediaDAOImpl implements SocialMediaDao {
    private static SocialMediaDAOImpl socialDAO = null; 
    public static SocialMediaDAOImpl instance(){
        if(socialDAO == null){
            socialDAO = new SocialMediaDAOImpl(); 
        }
        return socialDAO; 
    }
    
    @Override
    public void createUser(Account account) {
        // TODO Auto-generated method stub
        Connection connection = ConnectionUtil.getConnection(); 
        try {
            String sql = "insert into account values(?,?)"; 
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS); 
            preparedStatement.setString(1,account.getUsername());
            preparedStatement.setString(2,account.getPassword());

            preparedStatement.executeUpdate();


        } catch (SQLException e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        throw new UnsupportedOperationException("Unimplemented method 'createUser'");
    }

    @Override
    public void createNewMessage(Message message) {
        // TODO Auto-generated method stub
        Connection connection = ConnectionUtil.getConnection(); 
        try {
            String sql = "insert into message values(?,?,?)"; 
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1,message.getPosted_by()); 
            preparedStatement.setString(2,message.getMessage_text());
            preparedStatement.setLong(3,message.getTime_posted_epoch());

            preparedStatement.executeUpdate(); 
            
        } catch (SQLException e) {
            // TODO: handle exception
            e.printStackTrace();
        }
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
    public List<Message> getAllMessagesByMessageId(int message_id) {
        // TODO Auto-generated method stub
        Connection connection = ConnectionUtil.getConnection(); 
        List<Message> messages = new ArrayList<>(); 
        try {
            String sql = "select * from message where message_id = ?"; 
            PreparedStatement preparedStatement = connection.prepareStatement(sql); 
            preparedStatement.setInt(1, message_id);
            ResultSet rs = preparedStatement.executeQuery(); 

            while(rs.next()){
                messages.add(new Message(rs.getInt("message_id"), rs.getInt("posted_by"), rs.getString("message_text"), rs.getLong("time_posted_epoch"))); 
            }
            
        } catch (SQLException e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return messages; 
    }

    @Override
    public void deleteMessageByMessageId(int message_id) {
        // TODO Auto-generated method stub
        
        throw new UnsupportedOperationException("Unimplemented method 'deleteMessageById'");
    }

    @Override
    public void updateMessageByMessageId(int message_id, Message message) {
        // TODO Auto-generated method stub
        Connection connection = ConnectionUtil.getConnection(); 

        try {
            String sql = "update message set posted_by=?, message_text=?, time_posted_epoch=? where message_id=?"; 
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, message.getPosted_by()); 
            preparedStatement.setString(2, message.getMessage_text());
            preparedStatement.setLong(3,message.getTime_posted_epoch());
            preparedStatement.setInt(4,message_id);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } 

    }

    @Override
    public List<Message> getAllMessagesByAccountId(int account_id) {
        Connection connetcion = ConnectionUtil.getConnection(); 
        List<Message> messages = new ArrayList<>(); 
        try {
            
            String sql = "select * from messages where posted_by = ? "; 
            PreparedStatement preparedStatement = connetcion.prepareStatement(sql);
            preparedStatement.setInt(1, account_id); 
            ResultSet rs = preparedStatement.executeQuery(); 
            while (rs.next()) {
                messages.add(new Message(rs.getInt("message_id"), rs.getInt("posted_by"), rs.getString("message_text"), rs.getLong("time_posted_epoch"))); 
            }


        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } 
        return messages;         
    }

}
