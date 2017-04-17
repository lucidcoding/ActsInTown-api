package uk.co.luciditysoftware.actsintown.api.requests.message;

import java.util.UUID;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

public class CreateRequest {
    @NotNull(message = "This field is required")
    private UUID conversationId;

    @NotNull(message = "This field is required")
    @Length(max = 255)
    private String body;

    public UUID getConversationId() {
        return conversationId;
    }

    public void setConversationId(UUID conversationId) {
        this.conversationId = conversationId;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
