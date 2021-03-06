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

import uk.co.luciditysoftware.actsintown.domain.entities.UserType;
import uk.co.luciditysoftware.actsintown.domain.entities.UserUserType;
import uk.co.luciditysoftware.actsintown.domain.repositorycontracts.UserTypeRepository;

@Repository
@Scope("prototype")
public class UserTypeRepositoryImpl implements UserTypeRepository {

    @Autowired
    private SessionFactory sessionFactory;
    
    public List<UserType> getByIds(List<UUID> ids) {
        Session session = sessionFactory.getCurrentSession();
        
        @SuppressWarnings("unchecked")
        List<UserType> userTypes = session.createCriteria(UserType.class)
            .add( Restrictions.in("id", ids) )
            .list();
        
        return userTypes;
    }
    
    public List<UserType> getAll() {
        Session session = sessionFactory.getCurrentSession();
        @SuppressWarnings("unchecked")
        List<UserType> userTypes = session.createCriteria(UserType.class).addOrder(Order.asc("order")).list();
        return userTypes;
    }
    
    public List<UserType> getByUsername(String username) {
        Session session = sessionFactory.getCurrentSession();
        
        @SuppressWarnings("unchecked")
        List<UserType> userTypes = session.createCriteria(UserUserType.class)
            .createAlias("user", "user")
            .add( Restrictions.eq("user.username", username) )
            .setProjection(Projections.property("userType"))
            .list();
        
        return userTypes;
    }
}
