package uk.co.luciditysoftware.actsintown.domain.repositorycontracts;

import java.util.List;
import java.util.UUID;

import uk.co.luciditysoftware.actsintown.domain.entities.Message;

public interface MessageRepository {
    public List<Message> getByConversationId(UUID conversationId, int page, int pageSize);
    public void save(Message message);
}