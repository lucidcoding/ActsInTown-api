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
import uk.co.luciditysoftware.actsintown.domain.entities.Town;
import uk.co.luciditysoftware.actsintown.domain.repositorycontracts.TownRepository;

@Repository
@Scope("prototype")
public class TownRepositoryImpl implements TownRepository {

    @Autowired
    private SessionFactory sessionFactory;
    
    public Town getById(UUID id){
        Session session = sessionFactory.getCurrentSession();
        Town town = (Town)session.get(Town.class, id);
        return town;
    }
    
    public List<Town> getAll() {
        Session session = sessionFactory.getCurrentSession();
        @SuppressWarnings("unchecked")
        
        List<Town> towns = session
            .createCriteria(Town.class)
            .addOrder(Order.asc("name")).list();
        
        return towns;
    }
    
    public List<Town> getByCountyId(UUID countyId) {
        Session session = sessionFactory.getCurrentSession();
        @SuppressWarnings("unchecked")
        
        List<Town> role = session
            .createCriteria(Town.class)
            .createAlias("county", "county")
            .add(Restrictions.eq("county.id", countyId))
            .addOrder(Order.asc("name")).list();
        
        return role;
    }
}
