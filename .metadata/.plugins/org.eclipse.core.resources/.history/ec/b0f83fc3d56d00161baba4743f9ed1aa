package uk.co.luciditysoftware.actsintown.data.repositories;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
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
	
	public List<Town> getAll() {
		Session session = sessionFactory.getCurrentSession();
		@SuppressWarnings("unchecked")
		List<Town> role = session.createCriteria(Town.class).list();
		return role;
	}
}
