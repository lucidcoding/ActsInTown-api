package uk.co.luciditysoftware.actsintown.data.repositories;

import java.util.List;
import java.util.UUID;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import uk.co.luciditysoftware.actsintown.domain.entities.UserType;
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
		List<UserType> userTypes = session.createCriteria(UserType.class).list();
		return userTypes;
	}
}