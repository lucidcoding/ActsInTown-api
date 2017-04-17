package uk.co.luciditysoftware.actsintown.api.requests.conversation;

import java.util.List;
import java.util.UUID;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

public class StartRequest {

    @NotNull(message = "This field is required")
    private List<UUID> usersToIds;

    @NotNull(message = "This field is required")
    @Length(max = 255)
    private String messageBody;
    
    public List<UUID> getUsersToIds() {
        return usersToIds;
    }
    
    public void setUsersToIds(List<UUID> usersToIds) {
        this.usersToIds = usersToIds;
    }
    
    public String getMessageBody() {
        return messageBody;
    }
    
    public void setMessageBody(String messageBody) {
        this.messageBody = messageBody;
    }
}
