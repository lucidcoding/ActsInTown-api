package uk.co.luciditysoftware.actsintown.api.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import uk.co.luciditysoftware.actsintown.api.datatransferobjects.UserTypeDto;
import uk.co.luciditysoftware.actsintown.api.mappers.dtomappers.GenericDtoMapper;
import uk.co.luciditysoftware.actsintown.domain.entities.UserType;
import uk.co.luciditysoftware.actsintown.domain.repositorycontracts.UserTypeRepository;

/**
 * Controller class that handles all use cases performing actions on the user type entity.
 * @author Paul Davies
 */
@RestController
@RequestMapping("/usertype")
public class UserTypeController {

	@Autowired
	private UserTypeRepository userTypeRepository;

	@Autowired
	private GenericDtoMapper genericDtoMapper;
	
	/**
	 * Returns a list of all user types.
	 * @return List of user types
	 */
	@RequestMapping(value = "", method = RequestMethod.GET)
	@ResponseBody
	@Transactional
	public List<UserTypeDto> get() {
		List<UserType> userTypes = userTypeRepository.getAll();
		List<UserTypeDto> userTypeDtos = genericDtoMapper.map(userTypes, UserTypeDto.class);
		return userTypeDtos;
	}
	
	/**
	 * Returns a list of user types that the current logged in user has registered as.
	 * @return List of user types
	 */
	@RequestMapping(value = "/for-current-user", method = RequestMethod.GET)
	@ResponseBody
	@Transactional
	public List<UserTypeDto> getForCurrentUser() {
		String username = (String) SecurityContextHolder
			.getContext()
			.getAuthentication()
			.getPrincipal();

		List<UserType> userTypes = userTypeRepository.getByUsername(username);
		List<UserTypeDto> userTypeDtos = genericDtoMapper.map(userTypes, UserTypeDto.class);
		return userTypeDtos;
	}
}
