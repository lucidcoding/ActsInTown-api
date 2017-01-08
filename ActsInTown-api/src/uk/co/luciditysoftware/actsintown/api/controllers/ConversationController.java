package uk.co.luciditysoftware.actsintown.api.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import uk.co.luciditysoftware.actsintown.api.datatransferobjects.ConversationDto;
import uk.co.luciditysoftware.actsintown.api.mappers.dtomappers.GenericDtoMapper;
import uk.co.luciditysoftware.actsintown.api.mappers.parametersetmappers.conversation.StartParameterSetMapper;
import uk.co.luciditysoftware.actsintown.api.mappers.responsemappers.ValidationMessageMapper;
import uk.co.luciditysoftware.actsintown.api.requests.conversation.StartRequest;
import uk.co.luciditysoftware.actsintown.api.utilities.CurrentUserResolver;
import uk.co.luciditysoftware.actsintown.api.utilities.RequestLogger;
import uk.co.luciditysoftware.actsintown.domain.common.ValidationMessage;
import uk.co.luciditysoftware.actsintown.domain.entities.Conversation;
import uk.co.luciditysoftware.actsintown.domain.parametersets.conversation.StartParameterSet;
import uk.co.luciditysoftware.actsintown.domain.repositorycontracts.ConversationRepository;

/**
 * Controller class that handles all use cases performing actions on the conversation entity.
 * @author Paul Davies
 */
@RestController
@RequestMapping("/conversation")
public class ConversationController {

    @Autowired
    private ConversationRepository conversationRepository;

    @Autowired
    private CurrentUserResolver currentUserResolver;

    @Autowired
    private RequestLogger requestLogger;
    
    @Autowired
    private ValidationMessageMapper validationMessageMapper;
    
    @Autowired
    private GenericDtoMapper genericDtoMapper;
    
    @Autowired
    private StartParameterSetMapper startParameterSetMapper;
    
    @RequestMapping(value = "/for-current-user/{page}/{pageSize}", method = RequestMethod.GET)
    @ResponseBody
    @Transactional
    public List<ConversationDto> getForCurrentUser(@PathVariable int page, @PathVariable int pageSize) {
        String username = currentUserResolver.getUsername();
        List<Conversation> conversations = conversationRepository.getByUsername(username, page, pageSize);
        List<ConversationDto> conversationDtos = genericDtoMapper.map(conversations, ConversationDto.class);
        return conversationDtos;
    }
    
    //TODO: ensure this is protected, that the user is allowed access to the conversation.
    @RequestMapping(value = "", method = RequestMethod.POST)
    @ResponseBody
    @Transactional
    public ResponseEntity<?> start(@RequestBody StartRequest request, BindingResult bindingResult) {
        requestLogger.log(request);
        List<ValidationMessage> modelValidationMessages = validationMessageMapper.map(bindingResult);
        
        if(!modelValidationMessages.isEmpty()) {
            return new ResponseEntity<List<ValidationMessage>>(modelValidationMessages, new HttpHeaders(), HttpStatus.BAD_REQUEST);
        }
        
        StartParameterSet parameterSet = startParameterSetMapper.map(request);
        Conversation conversation = Conversation.start(parameterSet);
        conversationRepository.save(conversation);
        return new ResponseEntity<Void>(new HttpHeaders(), HttpStatus.CREATED);
    }
}
