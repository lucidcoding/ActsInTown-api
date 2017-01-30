package uk.co.luciditysoftware.actsintown.domain.entities;

import java.util.Collection;
import java.util.Date;

import uk.co.luciditysoftware.actsintown.domain.common.Entity;

/**
 * Entity class representing a conversation in the Acts In Town system.
 * @author Paul Davies
 */
public class Conversation extends Entity {

    /**
     * The date and time on which this conversation was started
     */
    private Date startedOn;
    
    /**
     * Whether this conversation has been deleted.
     */
    private boolean deleted;
    
    /**
     * The date this conversation was last updated.
     */
    private Date updatedOn;
    
    /**
     * The users involved in this conversation
     */
    private Collection<ConversationUser> conversationUsers;
    
    /**
     * The messages that make up this conversation
     */
    private Collection<Message> messages;

    public Date getStartedOn() {
        return startedOn;
    }

    public void setStartedOn(Date startedOn) {
        this.startedOn = startedOn;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public Date getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(Date updatedOn) {
        this.updatedOn = updatedOn;
    }

    public Collection<ConversationUser> getConversationUsers() {
        return conversationUsers;
    }

    public void setConversationUsers(Collection<ConversationUser> conversationUsers) {
        this.conversationUsers = conversationUsers;
    }

    public Collection<Message> getMessages() {
        return messages;
    }

    public void setMessages(Collection<Message> messages) {
        this.messages = messages;
    }
}
