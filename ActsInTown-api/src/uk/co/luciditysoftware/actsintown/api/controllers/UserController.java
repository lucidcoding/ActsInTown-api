package uk.co.luciditysoftware.actsintown.api.controllers;

import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.validation.Valid;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
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
import uk.co.luciditysoftware.actsintown.api.mappers.responsemappers.ValidationMessageMapper;
import uk.co.luciditysoftware.actsintown.api.requests.user.ChangePasswordRequest;
import uk.co.luciditysoftware.actsintown.api.requests.user.EditCurrentRequest;
import uk.co.luciditysoftware.actsintown.api.requests.user.InitializePasswordResetRequest;
import uk.co.luciditysoftware.actsintown.api.requests.user.RegisterRequest;
import uk.co.luciditysoftware.actsintown.api.requests.user.ResetPasswordRequest;
import uk.co.luciditysoftware.actsintown.api.utilities.CurrentUserResolver;
import uk.co.luciditysoftware.actsintown.api.utilities.RequestLogger;
import uk.co.luciditysoftware.actsintown.domain.common.ValidationMessage;
import uk.co.luciditysoftware.actsintown.domain.common.ValidationMessageType;
import uk.co.luciditysoftware.actsintown.domain.entities.User;
import uk.co.luciditysoftware.actsintown.domain.parametersets.user.ChangePasswordParameterSet;
import uk.co.luciditysoftware.actsintown.domain.parametersets.user.EditParameterSet;
import uk.co.luciditysoftware.actsintown.domain.parametersets.user.RegisterParameterSet;
import uk.co.luciditysoftware.actsintown.domain.parametersets.user.ResetPasswordParameterSet;
import uk.co.luciditysoftware.actsintown.domain.repositorycontracts.UserRepository;

/**
 * Controller class that handles all use cases performing actions on the user entity.
 * @author Paul Davies
 */
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
    private ValidationMessageMapper validationMessageMapper;
    
    @Autowired
    private JavaMailSender mailSender;
        
    @Autowired
    private CurrentUserResolver currentUserResolver;
    
    @Autowired
    private Environment environment;
    
    /**
     * Registers a user based on the supplied request object.
     * @param request Request containing parameters for the user to be registered
     * @param bindingResult The binding result
     * @return Http response reporting the results of the request, containing validation errors if invalid
     * @throws NoSuchAlgorithmException
     * @throws JsonProcessingException
     */
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ResponseBody
    @Transactional
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest request, 
                                      BindingResult bindingResult)
                                      throws NoSuchAlgorithmException, JsonProcessingException {
        requestLogger.log(request);
        List<ValidationMessage> modelValidationMessages = validationMessageMapper.map(bindingResult);
        
        if(!modelValidationMessages.isEmpty()) {
            return new ResponseEntity<List<ValidationMessage>>(modelValidationMessages, new HttpHeaders(), HttpStatus.BAD_REQUEST);
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
        email.setText(environment.getProperty("uiBaseUrl") + "/user/verify/" + user.getVerificationToken());
        
        if(!environment.getProperty("emailFrom").equals("")) {
            email.setFrom(environment.getProperty("emailFrom"));
        }
        
        mailSender.send(email);
        
        return new ResponseEntity<Void>(new HttpHeaders(), HttpStatus.CREATED);
    }
    
    /**
     * Verifies a user as having a valid email address.
     * @param verificationToken The verification token that was sent to the registered email address for the user
     * @return Http response reporting the results of the request, containing validation errors if invalid
     */
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

    /**
     * Edits a the current logged in user with the values supplied in the request object.
     * @param request Request object containing values to edit the user
     * @param bindingResult The binding result
     * @return Http response reporting the results of the request, containing validation errors if invalid
     * @throws NoSuchAlgorithmException
     * @throws JsonProcessingException
     */
    @RequestMapping(value = "/edit-current", method = RequestMethod.PUT)
    @ResponseBody
    @Transactional
    public ResponseEntity<?> editCurrent(@Valid @RequestBody EditCurrentRequest request, 
                                  BindingResult bindingResult)
                                  throws NoSuchAlgorithmException, JsonProcessingException {
        requestLogger.log(request);
        List<ValidationMessage> modelValidationMessages = validationMessageMapper.map(bindingResult);
        
        if(!modelValidationMessages.isEmpty()) {
            return new ResponseEntity<List<ValidationMessage>>(modelValidationMessages, new HttpHeaders(), HttpStatus.BAD_REQUEST);
        }
        
        User user = currentUserResolver.getUser();

        if (user == null) {
            return new ResponseEntity<Void>(new HttpHeaders(), HttpStatus.BAD_REQUEST);
        }

        EditParameterSet parameterSet = editParameterSetMapper.map(request);
        user.edit(parameterSet);
        userRepository.save(user);
        return new ResponseEntity<Void>(new HttpHeaders(), HttpStatus.OK);
    }
    
    /**
     * Returns the current logged in user.
     * @return The current logged in user
     */
    @RequestMapping(value = "/current", method = RequestMethod.GET)
    @ResponseBody
    @Transactional
    public UserDto getCurrent() {
        User user = currentUserResolver.getUser();
        UserDto userDto = genericDtoMapper.map(user, UserDto.class);
        return userDto;
    }
    
    /**
     * Changes the password for the current logged in user.
     * @param request Request object containing details for the password change
     * @param bindingResult The binding result
     * @return Http response reporting the results of the request, containing validation errors if invalid
     * @throws NoSuchAlgorithmException
     * @throws JsonProcessingException
     */
    @RequestMapping(value = "/change-password", method = RequestMethod.PUT)
    @ResponseBody
    @Transactional
    public ResponseEntity<?> changePassword(@Valid @RequestBody ChangePasswordRequest request, 
            BindingResult bindingResult)
            throws NoSuchAlgorithmException, JsonProcessingException {
        requestLogger.log(request);
        List<ValidationMessage> modelValidationMessages = validationMessageMapper.map(bindingResult);
        
        if(!modelValidationMessages.isEmpty()) {
            return new ResponseEntity<List<ValidationMessage>>(modelValidationMessages, new HttpHeaders(), HttpStatus.BAD_REQUEST);
        }
        
        User user = currentUserResolver.getUser();
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
    
    /**
     * Creates a password rest token and mails if out to the user specified in the request.
     * @param request Request object containing details of the user whose password is to be reset
     * @param bindingResult The binding result
     * @return Http response reporting the results of the request, containing validation errors if invalid
     * @throws NoSuchAlgorithmException
     * @throws JsonProcessingException
     */
    @RequestMapping(value = "/initialize-password-reset", method = RequestMethod.PUT)
    @ResponseBody
    @Transactional
    public ResponseEntity<?> initializePasswordReset(@Valid @RequestBody InitializePasswordResetRequest request, 
            BindingResult bindingResult)
            throws NoSuchAlgorithmException, JsonProcessingException {
        requestLogger.log(request);
        List<ValidationMessage> validationMessages = validationMessageMapper.map(bindingResult);
        
        if(!validationMessages.isEmpty()) {
            return new ResponseEntity<List<ValidationMessage>>(validationMessages, new HttpHeaders(), HttpStatus.BAD_REQUEST);
        }
        
        User user = userRepository.getByUsername(request.getUsername());
        user.initializePasswordReset();
        userRepository.save(user);
        
        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(user.getEmail());
        email.setSubject("Acts in Town Password Reset");
        email.setText(environment.getProperty("uiBaseUrl") + "/user/reset-password/" + user.getPasswordResetToken());

        if(!environment.getProperty("emailFrom").equals("")) {
            email.setFrom(environment.getProperty("emailFrom"));
        }
        
        mailSender.send(email);
        
        return new ResponseEntity<Void>(new HttpHeaders(), HttpStatus.OK);
    }

    /**
     * Resets the password of the requested user with the supplied password.
     * @param request Request object containing the username, new password and token of the password reset.
     * @param bindingResult The binding result
     * @return Http response reporting the results of the request, containing validation errors if invalid
     * @throws NoSuchAlgorithmException
     * @throws JsonProcessingException
     */
    @RequestMapping(value = "/reset-password", method = RequestMethod.PUT)
    @ResponseBody
    @Transactional
    public ResponseEntity<?> resetPassword(@Valid @RequestBody ResetPasswordRequest request, 
            BindingResult bindingResult)
            throws NoSuchAlgorithmException, JsonProcessingException {
        requestLogger.log(request);
        List<ValidationMessage> modelValidationMessages = validationMessageMapper.map(bindingResult);
        
        if(!modelValidationMessages.isEmpty()) {
            return new ResponseEntity<List<ValidationMessage>>(modelValidationMessages, new HttpHeaders(), HttpStatus.BAD_REQUEST);
        }

        User user = userRepository.getByUsername(request.getUsername());
        ResetPasswordParameterSet parameterSet = resetPasswordParameterSetMapper.map(request);
        List<ValidationMessage> domainValidationMessages = user.validateResetPassword(parameterSet);
        
        if(!domainValidationMessages.isEmpty()) {
            return new ResponseEntity<String>("Invalid password reset request.", new HttpHeaders(), HttpStatus.BAD_REQUEST);
        }
        
        user.resetPassword(parameterSet);
        userRepository.save(user);
        return new ResponseEntity<Void>(new HttpHeaders(), HttpStatus.OK);
    }
    
    /**
     * Returns a list of users specified by IDs.
     * @return List of users
     */
    @RequestMapping(value = "/by-ids", method = RequestMethod.POST)
    @ResponseBody
    @Transactional
    public List<UserDto> getByIds(@RequestBody List<UUID> ids) {
        List<User> users = userRepository.getByIds(ids);
        List<UserDto> userDtos = genericDtoMapper.map(users, UserDto.class);
        return userDtos;
    }
}
