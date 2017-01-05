package uk.co.luciditysoftware.actsintown.domain.entities;

import java.util.Date;
import java.util.UUID;

import uk.co.luciditysoftware.actsintown.domain.common.Entity;
import uk.co.luciditysoftware.actsintown.domain.parametersets.message.CreateParameterSet;

/**
 * Entity class representing a message in the Acts In Town system.
 * @author Paul Davies
 */
public class Message extends Entity {

    /**
     * The conversation this message belongs to
     */
    private Conversation conversation;
    
    /**
     * The user who created this message.
     */
    private User user;
    
    /**
     * The date this message was added.
     */
    private Date addedOn;
    
    /**
     * Whether this message has been deleted
     */
    private boolean deleted;

    /**
     * The body of the message.
     */
    private String body;
    
    public static Message create(CreateParameterSet parameterSet) {
        Message message = new Message();
        message.id = UUID.randomUUID();
        message.conversation = parameterSet.getConversation();
        message.user = parameterSet.getUser();
        message.body = parameterSet.getBody();
        message.addedOn = new Date();
        return message;
    }
    
    public Conversation getConversation() {
        return conversation;
    }

    public void setConversation(Conversation conversation) {
        this.conversation = conversation;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getAddedOn() {
        return addedOn;
    }

    public void setAddedOn(Date addedOn) {
        this.addedOn = addedOn;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
