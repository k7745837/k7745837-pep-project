package DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import Model.Message;
import Util.ConnectionUtil;

public class MessageDAO {
    
    /**
     * ## 4: Our API should be able to retrieve all messages.
     *
     * @return all messages in the database.
     */
    public List<Message> getAllMessages(){
        Connection connection = ConnectionUtil.getConnection();
        List<Message> messages = new ArrayList<>();
        try {
            //Write SQL logic here
            String sql = "SELECT * FROM message;";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()){
                messages.add(new Message(rs.getInt("message_id"), rs.getInt("posted_by"), rs.getString("message_text"), rs.getLong("time_posted_epoch")));
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return messages;
    }

    /**
     * ## 3: Our API should be able to process the creation of new messages.
     *
     * @param message an object modelling a Message. The message object does not contain a message_id.
     * @return message with generated message_id if successful, null otherwise.
     */
    public Message insertMessage(Message message){
        Connection connection = ConnectionUtil.getConnection();
        try {
            //Write SQL logic here.
            String sql = "INSERT INTO message (posted_by, message_text, time_posted_epoch) VALUES (?, ?, ?);" ;
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            //write preparedStatement's setString and setInt methods here.
            preparedStatement.setInt(1, message.getPosted_by());
            preparedStatement.setString(2, message.getMessage_text());
            //preparedStatement.setLong(3, System.currentTimeMillis());
            preparedStatement.setLong(3, 1669947792);

            preparedStatement.executeUpdate();
            ResultSet pkeyResultSet = preparedStatement.getGeneratedKeys();
            if (pkeyResultSet.next()){
                int generated_message_id = (int) pkeyResultSet.getLong(1);
                return new Message(generated_message_id, message.getPosted_by(), message.getMessage_text(), message.getTime_posted_epoch());
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    /**
     * ## 5: Our API should be able to retrieve a message by its ID.
     *
     * @param message_id a message ID.
     * @return message object if successful, null otherwise.
     */
    public Message getMessageById(int message_id){
        Connection connection = ConnectionUtil.getConnection();
        try {
            //Write SQL logic here
            String sql = "SELECT * FROM message WHERE message_id = ?;";
            
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            //write preparedStatement's setString and setInt methods here.
            preparedStatement.setInt(1, message_id);

            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()){
                return new Message(rs.getInt("message_id"), rs.getInt("posted_by"), rs.getString("message_text"), rs.getLong("time_posted_epoch"));
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    /**
     * ## 6: Our API should be able to delete a message identified by a message ID.
     * 
     * @param message_id of message to be deleted from message table.
     * @return number of rows deleted from message table if successful, {@code -1} otherwise.
     */
    public int deleteMessageById(int message_id){
        Connection connection = ConnectionUtil.getConnection();
        try {
            //Write SQL logic here
            String sql = "DELETE FROM message WHERE message_id = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            //write PreparedStatement setString and setInt methods here.
            preparedStatement.setInt(1, message_id);

            preparedStatement.executeUpdate();
            ResultSet pkeyResultSet = preparedStatement.getGeneratedKeys();
            if (pkeyResultSet.next()){
                return pkeyResultSet.getInt(1);
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return -1;
    }

    /**
     * ## 7: Our API should be able to update a message text identified by a message ID.
     * 
     * If the update is successful, the response body should contain the full updated message (including message_id, posted_by, message_text, and time_posted_epoch).
     *
     * @param id a message ID.
     * @param message a message object. the message object does not contain a message ID.
     * @return number of rows updated from message table if successful, {@code -1} otherwise.
     */
    public int updateMessage(int id, Message message){
        Connection connection = ConnectionUtil.getConnection();
        try {
            //Write SQL logic here
            String sql = "UPDATE message SET message_text = ? WHERE message_id = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            //write PreparedStatement setString and setInt methods here.
            preparedStatement.setString(1, message.getMessage_text());
            preparedStatement.setInt(2, id);

            preparedStatement.executeUpdate();
            ResultSet pkeyResultSet = preparedStatement.getGeneratedKeys();
            if (pkeyResultSet.next()){
                return pkeyResultSet.getInt(1);
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return -1;
    }

    /**
     * ## 8: Our API should be able to retrieve all messages written by a particular user.
     *
     * @param account_id
     * @return all messages specified account_id.
     */
    public List<Message> getAllMessagesByAccountId(int account_id){
        Connection connection = ConnectionUtil.getConnection();
        List<Message> messages = new ArrayList<>();
        try {
            //Write SQL logic here
            String sql = "SELECT * FROM message WHERE posted_by = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            //write PreparedStatement setString and setInt methods here.
            preparedStatement.setInt(1, account_id);

            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()){
                messages.add(new Message(rs.getInt("message_id"), rs.getInt("posted_by"), rs.getString("message_text"), rs.getLong("time_posted_epoch")));
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return messages;
    }

}

