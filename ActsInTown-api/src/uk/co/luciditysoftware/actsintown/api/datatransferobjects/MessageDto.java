package uk.co.luciditysoftware.actsintown.api.datatransferobjects;

import java.util.Date;
import java.util.UUID;

public class MessageDto {
    public UUID id;
    public ConversationDto conversation;
    public UserDto recipient;
    public UserDto sender;
    public Date sentOn;
    public boolean deleted;
    public boolean read;
    public String title;
    public String body;
}
