package uk.co.luciditysoftware.actsintown.domain.parametersets.message;

import uk.co.luciditysoftware.actsintown.domain.entities.User;

public class ReplyToParameterSet {
    private User sender;
    private String body;

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public String getBody() {
        return body;
    }
    
    public void setBody(String body) {
        this.body = body;
    }
}
