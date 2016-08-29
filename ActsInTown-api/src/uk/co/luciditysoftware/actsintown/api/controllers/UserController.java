package uk.co.luciditysoftware.actsintown.api.controllers;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import uk.co.luciditysoftware.actsintown.domain.parametersets.user.RegisterParameterSet;
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
	public ResponseEntity<Void> register(@RequestBody RegisterRequest request) throws NoSuchAlgorithmException  {
		RegisterParameterSet parameterSet = new RegisterParameterSet() {{
			this.setUsername(request.getUsername());
			this.setPassword(request.getPassword());
			this.setPasswordSalt(BCrypt.gensalt(HASHING_ROUNDS, SecureRandom.getInstanceStrong()));
			this.setFirstName(request.getFirstName());
			this.setLastName(request.getLastName());
			this.setRole(roleRepository.getById(UUID.fromString("2C6E33B8-BD7C-492C-807D-B4B1BCAE5F4F")));
		}};
		
		User user = User.register(parameterSet);
		userRepository.save(user);

		//http://websystique.com/springmvc/spring-mvc-4-restful-web-services-crud-example-resttemplate/
		HttpHeaders headers = new HttpHeaders();
        //headers.setLocation(ucBuilder.path("/user/{id}").buildAndExpand(user.getId()).toUri());
		return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
	}
}
