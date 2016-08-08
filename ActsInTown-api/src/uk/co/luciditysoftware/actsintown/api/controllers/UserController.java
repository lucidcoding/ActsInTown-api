package uk.co.luciditysoftware.actsintown.api.controllers;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import uk.co.luciditysoftware.actsintown.api.requests.user.RegisterRequest;
import uk.co.luciditysoftware.actsintown.domain.entities.Role;
import uk.co.luciditysoftware.actsintown.domain.entities.User;
import uk.co.luciditysoftware.actsintown.domain.repositorycontracts.RoleRepository;
import uk.co.luciditysoftware.actsintown.domain.repositorycontracts.UserRepository;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserRepository userRepository; 

	@Autowired
	private RoleRepository roleRepository; 

	private static final int HASHING_ROUNDS = 10;
	
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	@ResponseBody
	@Transactional
	public void register(@RequestBody RegisterRequest request) throws NoSuchAlgorithmException  {
		
		String passwordSalt = BCrypt.gensalt(HASHING_ROUNDS, SecureRandom.getInstanceStrong());;
		Role role = roleRepository.getById(UUID.fromString("2C6E33B8-BD7C-492C-807D-B4B1BCAE5F4F"));
		
		User user = User.register(
				request.getUsername(),
				request.getPassword(),
				passwordSalt,
				request.getFirstName(),
				request.getFirstName(), 
				role);
		
		userRepository.save(user);
		return;
	}
}
