package Service;

import java.util.List;

import DAO.AccountDAO;
import DAO.MessageDAO;
import Model.Message;

public class MessageService {

    MessageDAO messageDAO;
    AccountDAO accountDAO;

    /**
     * No-args constructor for a messageService instantiates a plain messageDAO.
     */
    public MessageService(){
        messageDAO = new MessageDAO();
        accountDAO = new AccountDAO();
    }

    /**
     * ## 3: Our API should be able to process the creation of new messages.
     * 
     * As a user, I should be able to submit a new message, which should be persisted to the database, but will not contain a message_id.
     * 
     * - The creation of the message will be successful if and only if the message_text is not blank, is not over 255 characters, 
     *      and posted_by refers to a real, existing user. If successful, the new message should be persisted to the database.
     *
     * @param message an object representing a new Message.
     * @return the newly added message if the add operation was successful, including the message_id.
     */
    public Message addMessage(Message message){
        if ((message.getMessage_text().isBlank()) || (message.getMessage_text().length() > 255) || !(accountDAO.checkAccountExistsById(message.getPosted_by()))){
            return null;
        }
        return messageDAO.insertMessage(message);
    }

    /**
     * ## 4: Our API should be able to retrieve all messages.
     * 
     * - It is expected for the list to simply be empty if there are no messages.
     *
     * @return all messages in the database.
     */
    public List<Message> getAllMessages() {
        return messageDAO.getAllMessages();
    }

    /**
     * ## 5: Our API should be able to retrieve a message by its ID.
     * 
     * - The response body should contain a JSON representation of the message identified by the message_id. It is expected for 
     *      the response body to simply be empty if there is no such message.
     * 
     * @param message_id a message ID.
     * @return message object if successful, null otherwise.
     */
    public Message getMessageById(int message_id){
        return messageDAO.getMessageById(message_id);
    }

    /**
     * ## 6: Our API should be able to delete a message identified by a message ID.
     * 
     * - The deletion of an existing message should remove an existing message from the database. If the message existed, 
     *      the response body should contain the now-deleted message.
     * - If the message did not exist, the response body should be empty. This is because the DELETE verb is intended to be idempotent,
     *       ie, multiple calls to the DELETE endpoint should respond with the same type of response.
     * 
     * @param message_id of message to be deleted from message table.
     * @return message that was deleted from message table if it existed, null otherwise.
     */
    public Message deleteMessageById(int message_id){
        Message old_message = messageDAO.getMessageById(message_id);
        if (old_message == null){
            return null;
        }
        messageDAO.deleteMessageById(message_id);
        return old_message;
    }

    /**
     * ## 7: Our API should be able to update a message text identified by a message ID.
     * 
     * The request body should contain a new message_text values to replace the message identified by message_id. The request body can not be guaranteed to contain any other information.
     * 
     * - The update of a message should be successful if and only if the message id already exists and the new message_text is not blank and is not over 255 characters. 
     *      If the update is successful, the response body should contain the full updated message (including message_id, posted_by, message_text, and time_posted_epoch), 
     *      and the message existing on the database should have the updated message_text.
     *
     * @param message_id the ID of the message to be modified.
     * @param message an object containing all data that should replace the values contained by the existing message_id. The message object does not contain a message ID.
     * @return the newly updated message if the update operation was successful. Return null if the update operation was unsuccessful.
     */
    public Message updateMessage(int message_id, Message message){
        Message old_message = messageDAO.getMessageById(message_id);
        if ((old_message == null) || (message.getMessage_text().isBlank()) || (message.getMessage_text().length() > 255)){
            return null;
        }
        messageDAO.updateMessage(message_id, message);
        Message new_message = messageDAO.getMessageById(message_id);
        return new_message;
    }

    /**
     * ## 8: Our API should be able to retrieve all messages written by a particular user.
     * 
     * - The response body should contain a JSON representation of a list containing all messages posted by a particular user, which is retrieved from the database. 
     *      It is expected for the list to simply be empty if there are no messages.
     *
     * @param account_id of the account to get posted messages from.
     * @return all messages posted by a specific account.
     */
    public List<Message> getAllMessagesFromCityToCity(int account_id) {
        return messageDAO.getAllMessagesByAccountId(account_id);
    }
}
