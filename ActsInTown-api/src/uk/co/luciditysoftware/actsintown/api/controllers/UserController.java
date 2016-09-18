package uk.co.luciditysoftware.actsintown.api.controllers;

import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;

import uk.co.luciditysoftware.actsintown.api.mappers.parametersetmappers.user.RegisterParameterSetMapper;
import uk.co.luciditysoftware.actsintown.api.requests.user.RegisterRequest;
import uk.co.luciditysoftware.actsintown.api.services.RequestLogger;
import uk.co.luciditysoftware.actsintown.domain.common.ValidationMessage;
import uk.co.luciditysoftware.actsintown.domain.common.ValidationMessageType;
import uk.co.luciditysoftware.actsintown.domain.entities.User;
import uk.co.luciditysoftware.actsintown.domain.parametersets.user.RegisterParameterSet;
import uk.co.luciditysoftware.actsintown.domain.repositorycontracts.UserRepository;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RegisterParameterSetMapper registerParameterSetMapper;

	@Autowired
	private RequestLogger requestLogger;

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	@ResponseBody
	@Transactional
	public ResponseEntity<?> register(
			@Valid @RequestBody RegisterRequest request, 
			BindingResult bindingResult)
			throws NoSuchAlgorithmException, JsonProcessingException {
		if (bindingResult.hasErrors()) {
			List<ValidationMessage> validationMessages = bindingResult
					.getAllErrors()
					.stream()
					.map(error -> new ValidationMessage(ValidationMessageType.ERROR,
							(error instanceof FieldError) ? ((FieldError)error).getField() : null, 
							error.getDefaultMessage()))
					.collect(Collectors.toList());

			return new ResponseEntity<List<ValidationMessage>>(validationMessages, new HttpHeaders(), HttpStatus.BAD_REQUEST);
        }
		
		requestLogger.log(request);

		// Check for existing user.
		User existingUser = userRepository.getByUsername(request.getUsername());

		if (existingUser != null) {
			return new ResponseEntity<Void>(new HttpHeaders(), HttpStatus.CONFLICT);
		}

		RegisterParameterSet parameterSet = registerParameterSetMapper.map(request);
		User user = User.register(parameterSet);
		userRepository.save(user);
		return new ResponseEntity<Void>(new HttpHeaders(), HttpStatus.CREATED);
	}
}
