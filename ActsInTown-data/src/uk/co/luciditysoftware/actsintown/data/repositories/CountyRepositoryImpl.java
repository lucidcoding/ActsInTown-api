package uk.co.luciditysoftware.actsintown.data.repositories;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import uk.co.luciditysoftware.actsintown.domain.entities.County;
import uk.co.luciditysoftware.actsintown.domain.repositorycontracts.CountyRepository;

@Repository
@Scope("prototype")
public class CountyRepositoryImpl implements CountyRepository {

    @Autowired
    private SessionFactory sessionFactory;
    
    public List<County> getAll(){
        Session session = sessionFactory.getCurrentSession();
        @SuppressWarnings("unchecked")
        
        List<County> counties = session
            .createCriteria(County.class)
            .addOrder(Order.asc("name")).list();
        
        return counties;
    }
}
