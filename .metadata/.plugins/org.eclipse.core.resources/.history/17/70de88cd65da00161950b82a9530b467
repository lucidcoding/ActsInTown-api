package uk.co.luciditysoftware.actsintown.api.controllers;

import java.util.List;
import java.util.UUID;

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

import uk.co.luciditysoftware.actsintown.api.datatransferobjects.MessageDto;
import uk.co.luciditysoftware.actsintown.api.mappers.dtomappers.GenericDtoMapper;
import uk.co.luciditysoftware.actsintown.api.mappers.parametersetmappers.message.CreateParameterSetMapper;
import uk.co.luciditysoftware.actsintown.api.mappers.responsemappers.ValidationMessageMapper;
import uk.co.luciditysoftware.actsintown.api.requests.message.CreateRequest;
import uk.co.luciditysoftware.actsintown.api.utilities.RequestLogger;
import uk.co.luciditysoftware.actsintown.domain.common.ValidationMessage;
import uk.co.luciditysoftware.actsintown.domain.entities.Message;
import uk.co.luciditysoftware.actsintown.domain.parametersets.message.CreateParameterSet;
import uk.co.luciditysoftware.actsintown.domain.repositorycontracts.MessageRepository;

/**
 * Controller class that handles all use cases performing actions on the message entity.
 * @author Paul Davies
 */
@RestController
@RequestMapping("/message")
public class MessageController {

    @Autowired
    private MessageRepository messageRepository;
    
    @Autowired
    private GenericDtoMapper genericDtoMapper;

    @Autowired
    private RequestLogger requestLogger;
    
    @Autowired
    private ValidationMessageMapper validationMessageMapper;
    
    @Autowired
    private CreateParameterSetMapper createParameterSetMapper;
    
    @RequestMapping(value = "/for-conversation/{conversationId}/{page}/{pageSize}", method = RequestMethod.GET)
    @ResponseBody
    @Transactional
    public List<MessageDto> getForCurrentUser(@PathVariable UUID conversationId, @PathVariable int page, @PathVariable int pageSize) {
        List<Message> messages = messageRepository.getByConversationId(conversationId, page, pageSize);
        List<MessageDto> messageDtos = genericDtoMapper.map(messages, MessageDto.class);
        return messageDtos;
    }

    //TODO: ensure this is protected, that the user is allowed access to the conversation.
    @RequestMapping(value = "", method = RequestMethod.POST)
    @ResponseBody
    @Transactional
    public ResponseEntity<?> create(@RequestBody CreateRequest request, BindingResult bindingResult) {
        requestLogger.log(request);
        List<ValidationMessage> modelValidationMessages = validationMessageMapper.map(bindingResult);
        
        if(!modelValidationMessages.isEmpty()) {
            return new ResponseEntity<List<ValidationMessage>>(modelValidationMessages, new HttpHeaders(), HttpStatus.BAD_REQUEST);
        }
        
        CreateParameterSet parameterSet = createParameterSetMapper.map(request);
        Message message = Message.create(parameterSet);
        messageRepository.save(message);
        return new ResponseEntity<Void>(new HttpHeaders(), HttpStatus.CREATED);
    }
}
