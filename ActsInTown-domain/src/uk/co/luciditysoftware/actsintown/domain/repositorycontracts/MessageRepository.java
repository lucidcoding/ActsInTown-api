package uk.co.luciditysoftware.actsintown.domain.repositorycontracts;

import java.util.List;
import java.util.UUID;

import uk.co.luciditysoftware.actsintown.domain.entities.Message;

public interface MessageRepository {
    public Message getById(UUID id);
    public List<Message> getByRecipientId(UUID recipientId, int page, int pageSize);
    public int getByRecipientIdCount(UUID recipientId);
    public List<Message> getBySenderId(UUID senderId, int page, int pageSize);
    public int getBySenderIdCount(UUID senderId);
    public void save(Message message);
}
