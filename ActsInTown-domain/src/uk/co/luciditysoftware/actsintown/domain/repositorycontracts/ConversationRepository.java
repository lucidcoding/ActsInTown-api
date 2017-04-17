package uk.co.luciditysoftware.actsintown.domain.repositorycontracts;

import java.util.UUID;

import uk.co.luciditysoftware.actsintown.domain.entities.Conversation;

public interface ConversationRepository {
    public Conversation getById(UUID id);
    public void save(Conversation conversation);
}
