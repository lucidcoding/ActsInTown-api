package uk.co.luciditysoftware.actsintown.domain.entities;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.UUID;
import java.util.stream.Collectors;

import uk.co.luciditysoftware.actsintown.domain.common.Entity;
import uk.co.luciditysoftware.actsintown.domain.parametersets.conversation.StartParameterSet;

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
    protected Date updatedOn;
    
    /**
     * The users involved in this conversation
     */
    private Collection<ConversationUser> conversationUsers;
    
    /**
     * The messages that make up this conversation
     */
    private Collection<Message> messages;

    public static Conversation start(StartParameterSet parameterSet) {
        Date now = new Date();
        
        Conversation conversation = new Conversation();
        conversation.id = UUID.randomUUID();
        conversation.startedOn = now;
        conversation.deleted = false;
        conversation.updatedOn = now;
        
        conversation.conversationUsers = (parameterSet
            .getUsersTo()
            .stream()
            .map(user -> new ConversationUser(){{
                this.id = UUID.randomUUID();
                this.setJoinedOn(now);
                this.setConversation(conversation);
                this.setUser(user);
            }}).collect(Collectors.toList()));
        
        conversation.conversationUsers.add(new ConversationUser(){{
            this.id = UUID.randomUUID();
            this.setJoinedOn(now);
            this.setConversation(conversation);
            this.setUser(parameterSet.getUserFrom());
        }});
        
        conversation.messages = new ArrayList<Message>();
        Message message = new Message();
        message.setId(UUID.randomUUID());
        message.setAddedOn(now);
        message.setBody(parameterSet.getMessageBody());
        message.setConversation(conversation);
        message.setDeleted(false);
        message.setUser(parameterSet.getUserFrom());
        conversation.messages.add(message);
        
        /*conversation.messages.add(new Message() {{
            this.id = UUID.randomUUID();
            this.setAddedOn(now);
            this.setBody(parameterSet.getMessageBody());
            this.setConversation(conversation);
            this.setDeleted(false);
            this.setUser(parameterSet.getUserFrom());
        }});*/
        
        return conversation;
    }
    
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
