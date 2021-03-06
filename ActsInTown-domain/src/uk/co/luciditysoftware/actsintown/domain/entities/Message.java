package uk.co.luciditysoftware.actsintown.domain.entities;

import java.util.Date;
import java.util.UUID;

import uk.co.luciditysoftware.actsintown.domain.common.Entity;
import uk.co.luciditysoftware.actsintown.domain.parametersets.message.ReplyToParameterSet;
import uk.co.luciditysoftware.actsintown.domain.parametersets.message.SendParameterSet;

/**
 * Entity class representing a message in the Acts In Town system.
 * @author Paul Davies
 */
public class Message extends Entity {
    /**
     * The conversation this message belongs to.
     */
    private Conversation conversation;
    
    /**
     * The user who sent this message
     */
    private User sender;
    
    /**
     * The user who this message was sent to
     */
    private User recipient;
    
    /**
     * The date this message was added.
     */
    private Date sentOn;
    
    /**
     * Whether this message has been deleted
     */
    private boolean deleted;

    /**
     * Whether this message has been read by the recipient
     */
    private boolean read;
    
    /**
     * The title of this message
     */
    private String title;
    
    /**
     * The body of the message.
     */
    private String body;
    
    public static Message send(SendParameterSet parameterSet) {
        Date now = new Date();
        Conversation conversation = new Conversation();
        conversation.setId(UUID.randomUUID());
        conversation.setStartedOn(now);
        Message message = new Message();
        message.id = UUID.randomUUID();
        message.conversation = conversation;
        message.sender = parameterSet.getSender();
        message.recipient = parameterSet.getRecipient();
        message.title = parameterSet.getTitle();
        message.body = parameterSet.getBody();
        message.sentOn = now;
        return message;
    }
    
    public Message replyTo(ReplyToParameterSet parameterSet) {
        Message message = new Message();
        message.id = UUID.randomUUID();
        message.conversation = conversation;
        message.recipient = sender;
        //message.sender = recipient;
        message.sender = parameterSet.getSender();
        message.body = parameterSet.getBody();
        message.sentOn = new Date();
        
        if(title.startsWith("Re: ")){
            message.title = title;
        } else {
            message.title = "Re: " + title;
        }
 
        return message;
    }

    public void markAsRead() {
        read = true;
    }

    public Conversation getConversation() {
        return conversation;
    }

    public void setConversation(Conversation conversation) {
        this.conversation = conversation;
    }
    
    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public User getRecipient() {
        return recipient;
    }

    public void setRecipient(User recipient) {
        this.recipient = recipient;
    }

    public Date getSentOn() {
        return sentOn;
    }

    public void setSentOn(Date sentOn) {
        this.sentOn = sentOn;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public boolean isRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
