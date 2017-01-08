package uk.co.luciditysoftware.actsintown.data.repositories;

import java.util.List;
import java.util.UUID;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import uk.co.luciditysoftware.actsintown.domain.entities.Message;
import uk.co.luciditysoftware.actsintown.domain.repositorycontracts.MessageRepository;

@Repository
@Scope("prototype")
public class MessageRepositoryImpl implements MessageRepository{

    @Autowired
    private SessionFactory sessionFactory;
    
    public List<Message> getByConversationId(UUID conversationId, int page, int pageSize) {
        Session session = sessionFactory.getCurrentSession();

        @SuppressWarnings("unchecked")
        List<Message> messages = session
            .createCriteria(Message.class)
            .createAlias("conversation", "conversation")
            .add(Restrictions.eq("conversation.id", conversationId))
            .addOrder(Order.desc("addedOn"))
            .setFirstResult((page - 1) * pageSize)
            .setMaxResults(pageSize)
            .list();
        
        return messages;
    }

    public void save(Message message) {
        Session session = sessionFactory.getCurrentSession();
        session.save(message);
        return;
    }
}