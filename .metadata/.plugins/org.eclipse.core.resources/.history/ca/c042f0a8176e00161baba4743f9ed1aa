package uk.co.luciditysoftware.actsintown.data.repositories;

import java.util.List;
import java.util.UUID;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import uk.co.luciditysoftware.actsintown.domain.entities.Spot;
import uk.co.luciditysoftware.actsintown.domain.repositorycontracts.SpotRepository;

@Repository
@Scope("prototype")
public class SpotRepositoryImpl implements SpotRepository {

	@Autowired
	private SessionFactory sessionFactory;
	
	public Spot getById(UUID id) {
		Session session = sessionFactory.getCurrentSession();
		Spot spot = (Spot)session.get(Spot.class, id);
		return spot;	
	}
	
	public List<Spot> getByUsername(String username) {
		Session session = sessionFactory.getCurrentSession();
		String hql = "from Spot as spot inner join spot.user as user where user.username = :username";
		Query query = session.createQuery(hql);
		query.setParameter("username", username);
		@SuppressWarnings("unchecked")
		List<Spot> spots = query.list();
		
		return spots;
	}
	
	public void save(Spot spot) {
		Session session = sessionFactory.getCurrentSession();
		session.save(spot);
		return;
	}
}
