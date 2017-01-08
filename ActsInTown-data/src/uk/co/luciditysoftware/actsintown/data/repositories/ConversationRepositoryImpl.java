package uk.co.luciditysoftware.actsintown.data.repositories;

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

import uk.co.luciditysoftware.actsintown.domain.entities.Conversation;
import uk.co.luciditysoftware.actsintown.domain.entities.ConversationUser;
import uk.co.luciditysoftware.actsintown.domain.entities.Message;
import uk.co.luciditysoftware.actsintown.domain.entities.Spot;
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
    
    public List<Conversation> getByUsername(String username, int page, int pageSize) {
        Session session = sessionFactory.getCurrentSession();

        @SuppressWarnings("unchecked")
        List<Conversation> conversation = session
            .createCriteria(ConversationUser.class)
            .createAlias("user", "user")
            .add(Restrictions.eq("user.username", username))
            .addOrder(Order.desc("updatedOn"))
            .setFirstResult((page - 1) * pageSize)
            .setMaxResults(pageSize)
            .setProjection(Projections.property("message"))
            .list();
        
        return conversation;
    }

    public void save(Conversation conversation) {
        Session session = sessionFactory.getCurrentSession();
        session.save(conversation);
        return;
    }
}