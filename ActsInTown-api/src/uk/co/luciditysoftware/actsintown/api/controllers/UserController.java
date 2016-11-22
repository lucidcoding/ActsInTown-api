package uk.co.luciditysoftware.actsintown.api.controllers;

import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;

import uk.co.luciditysoftware.actsintown.api.datatransferobjects.UserDto;
import uk.co.luciditysoftware.actsintown.api.mappers.dtomappers.GenericDtoMapper;
import uk.co.luciditysoftware.actsintown.api.mappers.parametersetmappers.user.ChangePasswordParameterSetMapper;
import uk.co.luciditysoftware.actsintown.api.mappers.parametersetmappers.user.EditParameterSetMapper;
import uk.co.luciditysoftware.actsintown.api.mappers.parametersetmappers.user.RegisterParameterSetMapper;
import uk.co.luciditysoftware.actsintown.api.mappers.parametersetmappers.user.ResetPasswordParameterSetMapper;
import uk.co.luciditysoftware.actsintown.api.requests.user.ChangePasswordRequest;
import uk.co.luciditysoftware.actsintown.api.requests.user.EditCurrentRequest;
import uk.co.luciditysoftware.actsintown.api.requests.user.InitializePasswordResetRequest;
import uk.co.luciditysoftware.actsintown.api.requests.user.RegisterRequest;
import uk.co.luciditysoftware.actsintown.api.requests.user.ResetPasswordRequest;
import uk.co.luciditysoftware.actsintown.api.services.RequestLogger;
import uk.co.luciditysoftware.actsintown.domain.common.ValidationMessage;
import uk.co.luciditysoftware.actsintown.domain.common.ValidationMessageType;
import uk.co.luciditysoftware.actsintown.domain.entities.User;
import uk.co.luciditysoftware.actsintown.domain.parametersets.user.ChangePasswordParameterSet;
import uk.co.luciditysoftware.actsintown.domain.parametersets.user.EditParameterSet;
import uk.co.luciditysoftware.actsintown.domain.parametersets.user.RegisterParameterSet;
import uk.co.luciditysoftware.actsintown.domain.parametersets.user.ResetPasswordParameterSet;
import uk.co.luciditysoftware.actsintown.domain.repositorycontracts.UserRepository;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RegisterParameterSetMapper registerParameterSetMapper;

	@Autowired
	private EditParameterSetMapper editParameterSetMapper;
	
	@Autowired
	private ChangePasswordParameterSetMapper changePasswordParameterSetMapper;

    @Autowired
    private ResetPasswordParameterSetMapper resetPasswordParameterSetMapper;
    
	@Autowired
	private RequestLogger requestLogger;

	@Autowired
	private GenericDtoMapper genericDtoMapper;
	
    @Autowired
    private JavaMailSender mailSender;
    
	//http://www.baeldung.com/registration-verify-user-by-email
		
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	@ResponseBody
	@Transactional
	public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest request, 
                                      BindingResult bindingResult)
                                      throws NoSuchAlgorithmException, JsonProcessingException {
		requestLogger.log(request);
		
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

		// Check for existing user.
		User existingUser = userRepository.getByUsername(request.getUsername());

		if (existingUser != null) {
			return new ResponseEntity<Void>(new HttpHeaders(), HttpStatus.CONFLICT);
		}

		RegisterParameterSet parameterSet = registerParameterSetMapper.map(request);
		User user = User.register(parameterSet);
		userRepository.save(user);
		
		SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(user.getEmail());
        email.setSubject("Acts in Town Verification Confirmation");
        email.setText("http://localhost:5555/user/verify/" + user.getVerificationToken());
        mailSender.send(email);
        
		return new ResponseEntity<Void>(new HttpHeaders(), HttpStatus.CREATED);
	}
	
	@RequestMapping(value = "/verify", method = RequestMethod.GET)
	@ResponseBody
	@Transactional
	public ResponseEntity<?> verify(@RequestParam("verificationToken") String verificationToken) {
		User user = userRepository.getByVerificationToken(verificationToken);
		
		if(user == null) {
			return new ResponseEntity<Void>(new HttpHeaders(), HttpStatus.NOT_FOUND);
		}
		
		List<ValidationMessage> validationMessages = user.validateVerify();
		
		if(!validationMessages.isEmpty()) {
			return new ResponseEntity<List<ValidationMessage>>(validationMessages, new HttpHeaders(), HttpStatus.BAD_REQUEST);
		}
		
		user.verify();
		userRepository.save(user);
		return new ResponseEntity<Void>(new HttpHeaders(), HttpStatus.OK);
	}

	@RequestMapping(value = "/edit-current", method = RequestMethod.PUT)
	@ResponseBody
	@Transactional
	public ResponseEntity<?> editCurrent(@Valid @RequestBody EditCurrentRequest request, 
			                      BindingResult bindingResult)
			                      throws NoSuchAlgorithmException, JsonProcessingException {
		requestLogger.log(request);
		
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

		String username = (String) SecurityContextHolder
			.getContext()
			.getAuthentication()
			.getPrincipal();
		
		User user = userRepository.getByUsername(username);

		if (user == null) {
			return new ResponseEntity<Void>(new HttpHeaders(), HttpStatus.BAD_REQUEST);
		}

		EditParameterSet parameterSet = editParameterSetMapper.map(request);
		user.edit(parameterSet);
		userRepository.save(user);
		return new ResponseEntity<Void>(new HttpHeaders(), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/current", method = RequestMethod.GET)
	@ResponseBody
	@Transactional
	public UserDto getCurrent() {
		String username = (String) SecurityContextHolder
			.getContext()
			.getAuthentication()
			.getPrincipal();

		User user = userRepository.getByUsername(username);
		UserDto userDto = genericDtoMapper.map(user, UserDto.class);
		return userDto;
	}
	
	@RequestMapping(value = "/change-password", method = RequestMethod.PUT)
	@ResponseBody
	@Transactional
	public ResponseEntity<?> changePassword(@Valid @RequestBody ChangePasswordRequest request, 
            BindingResult bindingResult)
            throws NoSuchAlgorithmException, JsonProcessingException {
		requestLogger.log(request);
		
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

		String username = (String) SecurityContextHolder
			.getContext()
			.getAuthentication()
			.getPrincipal();
		
		User user = userRepository.getByUsername(username);
		byte[] passwordBytes = Base64.decodeBase64(user.getPassword());
		
		if (!BCrypt.checkpw(request.getOldPassword(), new String(passwordBytes, StandardCharsets.UTF_8) )) {
			List<ValidationMessage> validationMessages = new ArrayList<ValidationMessage>();
			ValidationMessage validationMessage = new ValidationMessage();
			validationMessage.setType(ValidationMessageType.ERROR);
			validationMessage.setField("oldPassword");
			validationMessage.setText("Old password is incorrect.");
			validationMessages.add(validationMessage);
			return new ResponseEntity<List<ValidationMessage>>(validationMessages, new HttpHeaders(), HttpStatus.BAD_REQUEST);
		}
			
		ChangePasswordParameterSet parameterSet = changePasswordParameterSetMapper.map(request);
		user.changePassword(parameterSet);
		userRepository.save(user);
		return new ResponseEntity<Void>(new HttpHeaders(), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/initialize-password-reset", method = RequestMethod.PUT)
	@ResponseBody
	@Transactional
	public ResponseEntity<?> initializePasswordReset(@Valid @RequestBody InitializePasswordResetRequest request, 
            BindingResult bindingResult)
            throws NoSuchAlgorithmException, JsonProcessingException {
		requestLogger.log(request);
		
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
		
		User user = userRepository.getByUsername(request.getUsername());
		user.initializePasswordReset();
		userRepository.save(user);
		SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(user.getEmail());
        email.setSubject("Acts in Town Password Reset");
        email.setText("http://localhost:5555/user/reset-password/" + user.getPasswordResetToken());
        mailSender.send(email);
		return new ResponseEntity<Void>(new HttpHeaders(), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/reset-password", method = RequestMethod.PUT)
	@ResponseBody
	@Transactional
	public ResponseEntity<?> resetPassword(@Valid @RequestBody ResetPasswordRequest request, 
            BindingResult bindingResult)
            throws NoSuchAlgorithmException, JsonProcessingException {
		requestLogger.log(request);
		
		if (bindingResult.hasErrors()) {
			List<ValidationMessage> validationMessages = bindingResult
					.getAllErrors()
					.stream()
					.map(error -> new ValidationMessage(ValidationMessageType.ERROR,
							(error instanceof FieldError) ? ((FieldError)error).getField() : null, 
							error.getDefaultMessage()))
					.collect(Collectors.toList());

			return new ResponseEntity<String>("Invalid password reset request.", new HttpHeaders(), HttpStatus.BAD_REQUEST);
        }

		User user = userRepository.getByUsername(request.getUsername());
		ResetPasswordParameterSet parameterSet = resetPasswordParameterSetMapper.map(request);
		List<ValidationMessage> validationMessages = user.validateResetPassword(parameterSet);
		
		if(!validationMessages.isEmpty()) {
			return new ResponseEntity<String>("Invalid password reset request.", new HttpHeaders(), HttpStatus.BAD_REQUEST);
		}
		
		user.resetPassword(parameterSet);
		userRepository.save(user);
		return new ResponseEntity<Void>(new HttpHeaders(), HttpStatus.OK);
	}
}
