package uk.co.luciditysoftware.actsintown.domain.entities;

import java.util.Date;

import uk.co.luciditysoftware.actsintown.domain.common.Entity;

/**
 * An entity class representing the relationship between users and conversation in the Acts In Town system.
 * @author Paul Davies
 */
public class ConversationUser extends Entity {
    
    /**
     * The conversation in this conversation/user relationship
     */
    private Conversation conversation;

    /**
     * The user in this conversation/user relationship
     */
    private User user;
    
    /**
     * The date the user joined the conversation.
     */
    private Date joinedOn;

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

    public Date getJoinedOn() {
        return joinedOn;
    }

    public void setJoinedOn(Date joinedOn) {
        this.joinedOn = joinedOn;
    }
}
