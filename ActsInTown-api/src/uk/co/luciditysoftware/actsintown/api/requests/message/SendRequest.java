package uk.co.luciditysoftware.actsintown.api.requests.message;

import java.util.UUID;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

public class SendRequest {
    @NotNull(message = "This field is required")
    private UUID recipientId;

    @NotNull(message = "This field is required")
    @Length(max = 255)
    private String body;

    @NotNull(message = "This field is required")
    @Length(max = 25)
    private String title;

    public UUID getRecipientId() {
        return recipientId;
    }

    public void setRecipient(UUID id) {
        this.recipientId = id;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
