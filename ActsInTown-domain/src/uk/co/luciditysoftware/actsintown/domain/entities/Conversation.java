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
     * The messages that make up this conversation
     */
    private Collection<Message> messages;
    
    public Date getStartedOn() {
        return startedOn;
    }

    public void setStartedOn(Date startedOn) {
        this.startedOn = startedOn;
    }

    public Collection<Message> getMessages() {
        return messages;
    }

    public void setMessages(Collection<Message> messages) {
        this.messages = messages;
    }
}
