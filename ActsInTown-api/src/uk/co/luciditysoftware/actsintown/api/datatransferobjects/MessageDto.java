package uk.co.luciditysoftware.actsintown.api.datatransferobjects;

import java.util.Date;
import java.util.UUID;

public class MessageDto {
    public UUID id;
    public ConversationDto conversation;
    public UserDto user;
    public Date addedOn;
    public boolean deleted;
}
