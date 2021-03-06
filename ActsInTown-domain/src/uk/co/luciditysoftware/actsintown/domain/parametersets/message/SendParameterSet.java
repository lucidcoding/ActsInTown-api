package uk.co.luciditysoftware.actsintown.domain.parametersets.message;

import uk.co.luciditysoftware.actsintown.domain.entities.User;

public class SendParameterSet {
    private User sender;
    private User recipient;
    private String title;
    private String body;

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
