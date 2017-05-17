package uk.co.luciditysoftware.actsintown.api.requests.message;

import java.util.UUID;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

public class ReplyToRequest {
    @NotNull(message = "This field is required")
    private UUID originalMessageId;

    @NotNull(message = "This field is required")
    @Length(max = 255)
    private String body;

    public UUID getOriginalMessageId() {
        return originalMessageId;
    }

    public void setOriginalMessageId(UUID originalMessageId) {
        this.originalMessageId = originalMessageId;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
