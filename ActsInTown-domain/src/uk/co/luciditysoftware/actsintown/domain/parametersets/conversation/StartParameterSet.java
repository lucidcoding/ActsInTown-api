package uk.co.luciditysoftware.actsintown.domain.parametersets.conversation;

import java.util.List;

import uk.co.luciditysoftware.actsintown.domain.entities.User;

public class StartParameterSet {
    private User userFrom;
    private List<User> usersTo;
    private String messageBody;

    public User getUserFrom() {
        return userFrom;
    }

    public void setUserFrom(User userFrom) {
        this.userFrom = userFrom;
    }

    public List<User> getUsersTo() {
        return usersTo;
    }

    public void setUsersTo(List<User> usersTo) {
        this.usersTo = usersTo;
    }    

    public String getMessageBody() {
        return messageBody;
    }
    
    public void setMessageBody(String messageBody) {
        this.messageBody = messageBody;
    }
}