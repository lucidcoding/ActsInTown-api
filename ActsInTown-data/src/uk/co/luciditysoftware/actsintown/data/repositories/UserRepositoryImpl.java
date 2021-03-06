package uk.co.luciditysoftware.actsintown.data.repositories;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import uk.co.luciditysoftware.actsintown.domain.entities.User;
import uk.co.luciditysoftware.actsintown.domain.repositorycontracts.UserRepository;

@Repository
@Scope("prototype")
public class UserRepositoryImpl implements UserRepository {

    @Autowired
    private SessionFactory sessionFactory;
    
    public User getById(UUID id) {
        Session session = sessionFactory.getCurrentSession();
        User user = (User)session.get(User.class, id);
        return user;
    }

    public List<User> getByIds(List<UUID> ids) {
        if (ids.size() == 0) return new ArrayList<User>();
        Session session = sessionFactory.getCurrentSession();
        
        @SuppressWarnings("unchecked")
        List<User> users = session.createCriteria(User.class)
            .add( Restrictions.in("id", ids) )
            .list();
        
        return users;
    }
    
    public User getByVerificationToken(String verificationToken) {
        Session session = sessionFactory.getCurrentSession();

        User user = (User) session.createCriteria(User.class)
                .add( Restrictions.eq("verificationToken", verificationToken) )
                .uniqueResult();
        
        return user;
    }
    
    public User getByUsername(String username) {
        Session session = sessionFactory.getCurrentSession();
        
        User user = (User) session.createCriteria(User.class)
            .add( Restrictions.eq("username", username) )
            .uniqueResult();
        
        return user;
    }
    
    public void save(User user) {
        Session session = sessionFactory.getCurrentSession();
        session.save(user);
    }
}
