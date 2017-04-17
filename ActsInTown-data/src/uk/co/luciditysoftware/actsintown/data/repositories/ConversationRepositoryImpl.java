package uk.co.luciditysoftware.actsintown.data.repositories;

import java.util.UUID;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import uk.co.luciditysoftware.actsintown.domain.entities.Conversation;
import uk.co.luciditysoftware.actsintown.domain.repositorycontracts.ConversationRepository;

@Repository
@Scope("prototype")
public class ConversationRepositoryImpl implements ConversationRepository{

    @Autowired
    private SessionFactory sessionFactory;
    
    public Conversation getById(UUID id) {
        Session session = sessionFactory.getCurrentSession();
        Conversation conversation = (Conversation)session.get(Conversation.class, id);
        return conversation;
    }

    public void save(Conversation conversation) {
        Session session = sessionFactory.getCurrentSession();
        session.save(conversation);
        return;
    }
}
