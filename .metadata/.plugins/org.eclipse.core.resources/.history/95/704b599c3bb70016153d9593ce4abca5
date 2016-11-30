package uk.co.luciditysoftware.actsintown.data.repositories;

import java.util.UUID;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import uk.co.luciditysoftware.actsintown.domain.entities.Role;
import uk.co.luciditysoftware.actsintown.domain.repositorycontracts.RoleRepository;

@Repository
@Scope("prototype")
public class RoleRepositoryImpl implements RoleRepository {

	@Autowired
	private SessionFactory sessionFactory;
	
	public Role getById(UUID id) {
		Session session = sessionFactory.getCurrentSession();
		Role role = (Role)session.get(Role.class, id);
		return role;
	}
	
	public void save(Role role) {
		Session session = sessionFactory.getCurrentSession();
		session.save(role);
	}
}
