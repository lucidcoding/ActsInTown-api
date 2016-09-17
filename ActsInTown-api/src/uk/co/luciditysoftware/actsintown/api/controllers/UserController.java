package uk.co.luciditysoftware.actsintown.api.controllers;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.UUID;

import org.apache.commons.codec.binary.Base64;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import uk.co.luciditysoftware.actsintown.api.filters.LoggingFilter;
import uk.co.luciditysoftware.actsintown.api.requests.user.RegisterRequest;
import uk.co.luciditysoftware.actsintown.domain.entities.User;
import uk.co.luciditysoftware.actsintown.domain.parametersets.user.RegisterParameterSet;
import uk.co.luciditysoftware.actsintown.domain.repositorycontracts.RoleRepository;
import uk.co.luciditysoftware.actsintown.domain.repositorycontracts.UserRepository;
import uk.co.luciditysoftware.actsintown.domain.repositorycontracts.UserTypeRepository;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserRepository userRepository; 

	@Autowired
	private RoleRepository roleRepository; 
	
	@Autowired
	private UserTypeRepository userTypeRepository;

	private static final int HASHING_ROUNDS = 10;
	private static final Logger log = LogManager.getLogger(LoggingFilter.class);

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	@ResponseBody
	@Transactional
	public ResponseEntity<Void> register(@RequestBody RegisterRequest request) throws NoSuchAlgorithmException, JsonProcessingException  {
		ObjectMapper mapper = new ObjectMapper();
		String requestJson = mapper.writeValueAsString(request);
		log.debug(requestJson);
	
		String salt = BCrypt.gensalt(HASHING_ROUNDS, SecureRandom.getInstanceStrong());
		byte[] encryptedPasswordBytes = BCrypt.hashpw(request.getPassword(), salt).getBytes();
		String encryptedPassword = Base64.encodeBase64String(encryptedPasswordBytes);
		
		RegisterParameterSet parameterSet = new RegisterParameterSet() {{
			this.setUsername(request.getUsername());
			this.setPassword(encryptedPassword);
			this.setPasswordSalt(salt);
			this.setFirstName(request.getFirstName());
			this.setLastName(request.getLastName());
			this.setRole(roleRepository.getById(UUID.fromString("2C6E33B8-BD7C-492C-807D-B4B1BCAE5F4F")));
			this.setUserTypes(userTypeRepository.getByIds(request.getUserTypeIds()));
		}};
		
		User user = User.register(parameterSet);
		userRepository.save(user);
		HttpHeaders headers = new HttpHeaders();
		return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
	}
}
