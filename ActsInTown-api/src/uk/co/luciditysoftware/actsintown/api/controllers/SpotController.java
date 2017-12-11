package uk.co.luciditysoftware.actsintown.api.controllers;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import uk.co.luciditysoftware.actsintown.api.datatransferobjects.SpotDto;
import uk.co.luciditysoftware.actsintown.api.mappers.dtomappers.GenericDtoMapper;
import uk.co.luciditysoftware.actsintown.api.mappers.parametersetmappers.spot.AddParameterSetMapper;
import uk.co.luciditysoftware.actsintown.api.mappers.responsemappers.ValidationMessageMapper;
import uk.co.luciditysoftware.actsintown.api.requests.spot.AddRequest;
import uk.co.luciditysoftware.actsintown.api.utilities.CurrentUserResolver;
import uk.co.luciditysoftware.actsintown.api.utilities.RequestLogger;
import uk.co.luciditysoftware.actsintown.domain.common.ValidationMessage;
import uk.co.luciditysoftware.actsintown.domain.entities.Spot;
import uk.co.luciditysoftware.actsintown.domain.entities.User;
import uk.co.luciditysoftware.actsintown.domain.enums.BookedState;
import uk.co.luciditysoftware.actsintown.domain.parametersets.spot.AddParameterSet;
import uk.co.luciditysoftware.actsintown.domain.repositorycontracts.SpotRepository;

/**
 * Controller class that handles all use cases performing actions on the spot entity.
 * @author Paul Davies
 */
@RestController
@RequestMapping("/spot")
public class SpotController {

    @Autowired
    private SpotRepository spotRepository;

    @Autowired
    private AddParameterSetMapper addParameterSetMapper;

    @Autowired
    private RequestLogger requestLogger;
        
    @Autowired
    private CurrentUserResolver currentUserResolver;

    @Autowired
    private GenericDtoMapper genericDtoMapper;

    @Autowired
    private ValidationMessageMapper validationMessageMapper;

    @Autowired
    private JavaMailSender mailSender;
    
    /**
     * Returns a list of all spots that have been added by the currently logged in user.
     * @return List of spots for current user
     */
    @RequestMapping(value = "/for-current-user", method = RequestMethod.GET)
    @ResponseBody
    @Transactional
    public List<SpotDto> getForCurrentUser() {
        String username = currentUserResolver.getUsername();
        List<Spot> spots = spotRepository.getByUsername(username);
        List<SpotDto> spotDtos = genericDtoMapper.map(spots, SpotDto.class);
        return spotDtos;
    }

    /**
     * Adds a spot for the current user with the supplied parameters.
     * @param request Request object containing values for the spot to be added.
     * @return Http response reporting the results of the request
     */
    @RequestMapping(value = "", method = RequestMethod.POST)
    @ResponseBody
    @Transactional
    public ResponseEntity<?> add(@RequestBody AddRequest request, BindingResult bindingResult) {
        requestLogger.log(request);
        List<ValidationMessage> modelValidationMessages = validationMessageMapper.map(bindingResult);
        
        if(!modelValidationMessages.isEmpty()) {
            return new ResponseEntity<List<ValidationMessage>>(modelValidationMessages, new HttpHeaders(), HttpStatus.BAD_REQUEST);
        }

        AddParameterSet parameterSet = addParameterSetMapper.map(request);
        List<ValidationMessage> validationMessages = Spot.validateAdd(parameterSet);

        if(!validationMessages.isEmpty()) {
            return new ResponseEntity<List<ValidationMessage>>(validationMessages, new HttpHeaders(), HttpStatus.BAD_REQUEST);
        } 
        
        Spot spot = Spot.add(parameterSet);
        spotRepository.save(spot);
        return new ResponseEntity<Void>(new HttpHeaders(), HttpStatus.CREATED);
    }

    /**
     * Returns a list of spots matching the supplied criteria.
     * @param startDate The start date for the period in which spots should be included
     * @param endDate The end date for the period in which spots should be included
     * @param townId The ID of the town in which spots should be included
     * @param bookedState The state of spots which should be included
     * @return List of matching spots
     */
    @RequestMapping(value = "/search", method = RequestMethod.GET)
    @ResponseBody
    @Transactional
    public List<SpotDto> search(
            @RequestParam("startDate") @DateTimeFormat(pattern="yyyy-MM-dd'T'HH:mm:ss.SSS'Z'") Date startDate, 
            @RequestParam("endDate") @DateTimeFormat(pattern="yyyy-MM-dd'T'HH:mm:ss.SSS'Z'") Date endDate,
            @RequestParam("townId") UUID townId,
            @RequestParam("bookedState") BookedState bookedState) {
        GregorianCalendar calEndDate = new GregorianCalendar();
        calEndDate.setTime(endDate);
        calEndDate.add(Calendar.DAY_OF_MONTH, 1);  
        List<Spot> spots = spotRepository.search(startDate, calEndDate.getTime(), townId, bookedState);
        List<SpotDto> spotDtos = genericDtoMapper.map(spots, SpotDto.class);
        return spotDtos;
    }
    
    /**
     * Deletes a spot
     * @return Http Response
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    @Transactional
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        String username = currentUserResolver.getUsername();
        Spot spot = spotRepository.getById(id);
        
        if(!spot.getUser().getUsername().equals(username)) {
            return new ResponseEntity<Void>(new HttpHeaders(), HttpStatus.FORBIDDEN);
        }
        
        spotRepository.delete(id);
        return new ResponseEntity<Void>(new HttpHeaders(), HttpStatus.NO_CONTENT);
    }

    /**
     * Allows a user to enquire about a spot.
     * @return Http Response
     */
    @RequestMapping(value = "/enquire-about/{id}", method = RequestMethod.PUT)
    @ResponseBody
    @Transactional
    public ResponseEntity<Void> enquireAbout(@PathVariable UUID id) {
        User user = currentUserResolver.getUser();
        Spot spot = spotRepository.getById(id);
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
                
        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(spot.getUser().getEmail());
        email.setSubject("Acts in Town enquiry");
        
        if(spot.getBookedState() == BookedState.AVAILABLE) {
            email.setText("Hi " + spot.getUser().getFirstName() + ",\r\n\r\n" +
                "User '" + user.getFirstName() + " " + user.getLastName() + "' would like to enquire about " +
                "the spot you have available at " + spot.getVenueName() + " " +
                "on " + dateFormat.format(spot.getScheduledFor()) + "\r\n\r\n" +
                "Please contact them on this email address: " + user.getEmail() + "\r\n\r\n" +
                "Regards,\r\n" +
                "The Acts In Town Team");
        } else {
            email.setText("Hi " + spot.getUser().getFirstName() + ",\r\n\r\n" +
                "User '" + user.getFirstName() + " " + user.getLastName() + "' would like to enquire about " +
                "the the fact that you are in town whilst performing at " + spot.getVenueName() + " " +
                "on " + dateFormat.format(spot.getScheduledFor()) + "\r\n\r\n" +
                "Please contact them on this email address: " + user.getEmail() + "\r\n\r\n" +
                "Regards,\r\n" +
                "The Acts In Town Team");
        }
        
        mailSender.send(email);
        
        return new ResponseEntity<Void>(new HttpHeaders(), HttpStatus.OK);
    }
}
