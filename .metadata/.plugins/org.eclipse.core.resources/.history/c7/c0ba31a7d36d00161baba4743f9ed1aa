package uk.co.luciditysoftware.actsintown.api.controllers;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import uk.co.luciditysoftware.actsintown.api.config.UserPrincipal;
import uk.co.luciditysoftware.actsintown.domain.entities.User;
import uk.co.luciditysoftware.actsintown.domain.repositorycontracts.UserRepository;

//http://stackoverflow.com/questions/7462202/spring-json-request-getting-406-not-acceptable

@RestController
@RequestMapping("/test")
public class TestController {
	
	@Autowired
	private UserRepository userRepository; 
	
	@RequestMapping(value = "", method = RequestMethod.GET)
	@ResponseBody
	public String get() {

	    /*@SuppressWarnings("unused")
	    Object auth = SecurityContextHolder.getContext().getAuthentication();
		@SuppressWarnings("unused")
		UserPrincipal user = (UserPrincipal)SecurityContextHolder.getContext().getAuthentication().getPrincipal();*/
		return "TEST";
	}
	
	@RequestMapping(value = "/data", method = RequestMethod.GET)
	@ResponseBody
	@Transactional
	public User testdata() {
		User user = userRepository.getById(UUID.fromString("B9A3886F-4120-45C8-B060-AC09A4386859"));
	    return user;
	}
}
