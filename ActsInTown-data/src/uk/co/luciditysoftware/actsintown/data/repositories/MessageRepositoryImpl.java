package uk.co.luciditysoftware.actsintown.data.repositories;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
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
    
    public Message getById(UUID id) {
        Session session = sessionFactory.getCurrentSession();
        Message message = (Message)session.get(Message.class, id);
        return message;
    }
    
    public List<Message> getByRecipientId(UUID recipientId, int page, int pageSize) {
        Session session = sessionFactory.getCurrentSession();

        @SuppressWarnings("unchecked")
        List<Message> messages = session
            .createCriteria(Message.class)
            .createAlias("recipient", "recipient")
            .add(Restrictions.eq("recipient.id", recipientId))
            .addOrder(Order.desc("sentOn"))
            .setFirstResult((page - 1) * pageSize)
            .setMaxResults(pageSize)
            .list();
        
        return messages;
    }
    
    public int getByRecipientIdCount(UUID recipientId) {
        Session session = sessionFactory.getCurrentSession();

        int count = ((Long)session
            .createCriteria(Message.class)
            .createAlias("recipient", "recipient")
            .add(Restrictions.eq("recipient.id", recipientId))
            .setProjection(Projections.rowCount())
            .uniqueResult())
            .intValue();
        
        return count;   
    }
    
    public List<Message> getBySenderId(UUID senderId, int page, int pageSize) {
        Session session = sessionFactory.getCurrentSession();

        @SuppressWarnings("unchecked")
        List<Message> messages = session
            .createCriteria(Message.class)
            .createAlias("sender", "sender")
            .add(Restrictions.eq("sender.id", senderId))
            .addOrder(Order.desc("sentOn"))
            .setFirstResult((page - 1) * pageSize)
            .setMaxResults(pageSize)
            .list();
        
        return messages;
    }
    
    public int getBySenderIdCount(UUID senderId) {
        Session session = sessionFactory.getCurrentSession();

        int count = ((Long)session
            .createCriteria(Message.class)
            .createAlias("sender", "sender")
            .add(Restrictions.eq("sender.id", senderId))
            .setProjection(Projections.rowCount())
            .uniqueResult())
            .intValue();
        
        return count;   
    }

    public List<Message> getByConversationId(UUID conversationId, Date before, int page, int pageSize) {
        Session session = sessionFactory.getCurrentSession();

        @SuppressWarnings("unchecked")
        List<Message> messages = session
            .createCriteria(Message.class)
            //.createAlias("sender", "sender")
            .add(Restrictions.eq("conversation.id", conversationId))
            .add(Restrictions.lt("sentOn", before))
            .addOrder(Order.desc("sentOn"))
            .setFirstResult((page - 1) * pageSize)
            .setMaxResults(pageSize)
            .list();
        
        return messages; 
    }
    
    public int getByConversationIdCount(UUID conversationId, Date before) {
        Session session = sessionFactory.getCurrentSession();

        int count = ((Long)session
            .createCriteria(Message.class)
            //.createAlias("sender", "sender")
            .add(Restrictions.eq("conversation.id", conversationId))
            .add(Restrictions.lt("sentOn", before))
            .setProjection(Projections.rowCount())
            .uniqueResult())
            .intValue();
        
        return count;   
    }
 
    public Message getLastByConversationId(UUID conversationId) {
        Session session = sessionFactory.getCurrentSession();

        Message message = (Message)session
            .createCriteria(Message.class)
            .add(Restrictions.eq("conversation.id", conversationId))
            .addOrder(Order.desc("sentOn"))
            .setMaxResults(1)
            .uniqueResult();
        
        return message; 
    }
    
    public void save(Message message) {
        Session session = sessionFactory.getCurrentSession();
        session.save(message);
        return;
    }
}
