package uk.co.luciditysoftware.actsintown.domain.repositorycontracts;

import java.util.List;

import uk.co.luciditysoftware.actsintown.domain.entities.Conversation;

public interface ConversationRepository {
    public List<Conversation> getByUsername(String username, int page, int pageSize);
    public void save(Conversation conversation);
}
