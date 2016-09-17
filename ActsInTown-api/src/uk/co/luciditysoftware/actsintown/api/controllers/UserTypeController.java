package uk.co.luciditysoftware.actsintown.api.controllers;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration.AccessLevel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import uk.co.luciditysoftware.actsintown.api.datatransferobjects.UserTypeDto;
import uk.co.luciditysoftware.actsintown.domain.entities.UserType;
import uk.co.luciditysoftware.actsintown.domain.repositorycontracts.UserTypeRepository;

@RestController
@RequestMapping("/usertype")
public class UserTypeController {

	@Autowired
	private UserTypeRepository userTypeRepository;
	
	@RequestMapping(value = "", method = RequestMethod.GET)
	@ResponseBody
	@Transactional
	public List<UserTypeDto> get() {
		Collection<UserType> userTypes = userTypeRepository.getAll();
		ModelMapper modelMapper = new ModelMapper();

		modelMapper
			.getConfiguration()
			.setFieldMatchingEnabled(true)
			.setMethodAccessLevel(AccessLevel.PRIVATE);

		List<UserTypeDto> userTypeDtos = userTypes
			.stream()
			.map(userType -> modelMapper.map(userType, UserTypeDto.class))
			.collect(Collectors.toList());

		return userTypeDtos;
	}
}
