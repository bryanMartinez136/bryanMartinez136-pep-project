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


    // dao object to access database and create a user. 
    @Override
    public Account createUser(Account account) {
        
        Connection connection = ConnectionUtil.getConnection(); 
        try {
            String sql = "insert into account (username, password) values(?,?)"; 
            //using return generated keys, we gaurentee each account created has a unique id.
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS); 
            preparedStatement.setString(1,account.getUsername());
            preparedStatement.setString(2,account.getPassword());

            preparedStatement.executeUpdate();
            
            ResultSet rs = preparedStatement.getGeneratedKeys(); 
            if(rs.next()){
                int generated_account_id = (int) rs.getLong(1); 
                return new Account(generated_account_id, account.getUsername(), account.getPassword()); 
            }

        } catch (SQLException e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        
        return null; 
    }
    
    public Account loginUser(Account account){
        Connection connection = ConnectionUtil.getConnection(); 

        try {
            String sql = "select * from account where username=? and password=? "; 
            PreparedStatement preparedStatement = connection.prepareStatement(sql); 
            preparedStatement.setString(1,account.getUsername());
            preparedStatement.setString(2,account.getPassword());
            ResultSet rs = preparedStatement.executeQuery(); 
            if(rs.next()){
                return new Account(rs.getInt("account_id"),rs.getString("username"), rs.getString("password")); 
            }


        } catch (SQLException e) {

        }

        return null; 

    }
    
    // private helper function to check if an account already exists
    private boolean accountExists(int account_id){
        Connection connection = ConnectionUtil.getConnection(); 
        int count = 0; 
        try {
            String sql = "select count(account_id) from account where account_id=?"; 
            PreparedStatement preparedStatement = connection.prepareStatement(sql); 
            preparedStatement.setInt(1, account_id); 
            
            ResultSet rs = preparedStatement.executeQuery(); 
            if(rs.next()){
                count = rs.getInt(1);
            } 
            
        } catch (Exception e) {
            // TODO: handle exception
        }
        // if count is greater than zero, then there exists an account in the databse with the account_id
        if(count>0){return true; }
        return false; 
    }

    @Override
    public Message createNewMessage(Message message) {

        // if the method returns false, there is no such account in the data base and
        //should return null. 
        if(!accountExists(message.getPosted_by())){return null; }

        //otherwise, continue as normal 
        Connection connection = ConnectionUtil.getConnection(); 
        try {
            String sql = "insert into message (posted_by, message_text, time_posted_epoch) values(?,?,?)"; 

            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1,message.getPosted_by()); 
            preparedStatement.setString(2,message.getMessage_text());
            preparedStatement.setLong(3,message.getTime_posted_epoch());

            preparedStatement.executeUpdate(); 
            ResultSet rs = preparedStatement.getGeneratedKeys(); 

            if(rs.next()){
                int message_id = (int)rs.getLong(1); 
                return new Message(message_id,message.getPosted_by(),  message.getMessage_text(), message.getTime_posted_epoch()); 
            }
            
            
        } catch (SQLException e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return null; 
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
        
        return messages; 
    }
    
        /*
         * 
         * 
        public Flight getFlightById(int id){
            Connection connection = ConnectionUtil.getConnection();
            try {
                //Write SQL logic here
                String sql = "select * from flight where flight_id = ?";
                
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
    
                //write preparedStatement's setString and setInt methods here.
                preparedStatement.setInt(1, id); 
    
                ResultSet rs = preparedStatement.executeQuery();
                while(rs.next()){
                    Flight flight = new Flight(rs.getInt("flight_id"), rs.getString("departure_city"),
                            rs.getString("arrival_city"));
                    return flight;
                }
            }catch(SQLException e){
                System.out.println(e.getMessage());
            }
            return null;
        }
         */
    

    @Override
    public Message getMessagesByMessageId(int message_id) {
        Connection connection = ConnectionUtil.getConnection(); 
        try {
            String sql = "select * from message where message_id = ?"; 
            PreparedStatement preparedStatement = connection.prepareStatement(sql); 
            preparedStatement.setInt(1, message_id);
            ResultSet rs = preparedStatement.executeQuery(); 
            while(rs.next()){
                Message message = new Message(rs.getInt("message_id"), rs.getInt("posted_by"),
                rs.getString("message_text"),rs.getLong("time_posted_epoch")); 
                return message; 
            }
            
        } catch (SQLException e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return null; 
    }

    @Override
    public void deleteMessageByMessageId(int message_id) {
        Connection connection = ConnectionUtil.getConnection(); 

        try {
            String sql = "delete from message where message_id = ? "; 
            PreparedStatement preparedStatement = connection.prepareStatement(sql); 
            preparedStatement.setInt(1, message_id);
            preparedStatement.executeUpdate(); 
        } catch (SQLException e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        
    }


    @Override
    public void updateMessageByMessageId(int message_id, Message message) {
        Connection connection = ConnectionUtil.getConnection(); 

        try {
            // string sql won't update if message id is not in the table. 
            String sql = "update message set message_text=? where message_id=?;"; 
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            // set parameters here
            preparedStatement.setString(1, message.getMessage_text());
            preparedStatement.setInt(2,message_id);
            preparedStatement.executeUpdate(); 
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } 

    }

    @Override
    public List<Message> getMessagesByAccountId(int account_id) {
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
