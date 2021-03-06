package uk.co.luciditysoftware.actsintown.api.controllers;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
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
import uk.co.luciditysoftware.actsintown.api.mappers.parametersetmappers.message.ReplyToParameterSetMapper;
import uk.co.luciditysoftware.actsintown.api.mappers.parametersetmappers.message.SendParameterSetMapper;
import uk.co.luciditysoftware.actsintown.api.mappers.responsemappers.ValidationMessageMapper;
import uk.co.luciditysoftware.actsintown.api.requests.message.ReplyToRequest;
import uk.co.luciditysoftware.actsintown.api.requests.message.SendRequest;
import uk.co.luciditysoftware.actsintown.api.utilities.CurrentUserResolver;
import uk.co.luciditysoftware.actsintown.api.utilities.RequestLogger;
import uk.co.luciditysoftware.actsintown.domain.common.ValidationMessage;
import uk.co.luciditysoftware.actsintown.domain.entities.Message;
import uk.co.luciditysoftware.actsintown.domain.parametersets.message.ReplyToParameterSet;
import uk.co.luciditysoftware.actsintown.domain.parametersets.message.SendParameterSet;
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
    private SendParameterSetMapper createParameterSetMapper;
    
    @Autowired
    private ReplyToParameterSetMapper replyParameterSetMapper;
    
    @Autowired
    private CurrentUserResolver currentUserResolver;
    
    @RequestMapping(value = "/{messageId}", method = RequestMethod.GET)
    @ResponseBody
    @Transactional
    public MessageDto get(@PathVariable UUID messageId) {
        Message message = messageRepository.getById(messageId);
        message.markAsRead();
        messageRepository.save(message);
        MessageDto messageDto = genericDtoMapper.map(message, MessageDto.class);
        return messageDto;
    }
    
    @RequestMapping(value = "/inbox/count", method = RequestMethod.GET)
    @ResponseBody
    @Transactional
    public int getInboxCount() {
        UUID recipientId = currentUserResolver.getUser().getId();
        int count = messageRepository.getByRecipientIdCount(recipientId);
        return count;
    }
    
    @RequestMapping(value = "/inbox/{page}/{pageSize}", method = RequestMethod.GET)
    @ResponseBody
    @Transactional
    public List<MessageDto> getInbox(@PathVariable int page, @PathVariable int pageSize) {
        UUID recipientId = currentUserResolver.getUser().getId();
        List<Message> messages = messageRepository.getByRecipientId(recipientId, page, pageSize);
        List<MessageDto> messageDtos = genericDtoMapper.map(messages, MessageDto.class);
        return messageDtos;
    }
    
    @RequestMapping(value = "/sent-items/count", method = RequestMethod.GET)
    @ResponseBody
    @Transactional
    public int getSentItemsCount() {
        UUID recipientId = currentUserResolver.getUser().getId();
        int count = messageRepository.getBySenderIdCount(recipientId);
        return count;
    }
    
    @RequestMapping(value = "/sent-items/{page}/{pageSize}", method = RequestMethod.GET)
    @ResponseBody
    @Transactional
    public List<MessageDto> getSentItems(@PathVariable int page, @PathVariable int pageSize) {
        UUID senderId = currentUserResolver.getUser().getId();
        List<Message> messages = messageRepository.getBySenderId(senderId, page, pageSize);
        List<MessageDto> messageDtos = genericDtoMapper.map(messages, MessageDto.class);
        return messageDtos;
    }
    
    
    @RequestMapping(value = "/for-conversation/{conversationId}/{before}/count", method = RequestMethod.GET)
    @ResponseBody
    @Transactional
    public int getForConversationCount(@PathVariable UUID conversationId, @PathVariable Date before) {
        int count = messageRepository.getByConversationIdCount(conversationId, before);
        return count;
    }
    
    @RequestMapping(value = "/for-conversation/{conversationId}/{before}/{page}/{pageSize}", method = RequestMethod.GET)
    @ResponseBody
    @Transactional
    public List<MessageDto> getForConversation(
            @PathVariable UUID conversationId,
            @PathVariable @DateTimeFormat(pattern="yyyy-MM-dd'T'HH:mm:ss.SSS'Z'") Date before,
            @PathVariable int page,
            @PathVariable int pageSize) {
        List<Message> messages = messageRepository.getByConversationId(conversationId, before, page, pageSize);
        List<MessageDto> messageDtos = genericDtoMapper.map(messages, MessageDto.class);
        return messageDtos;
    }
    
    @RequestMapping(value = "", method = RequestMethod.POST)
    @ResponseBody
    @Transactional
    public ResponseEntity<?> send(@RequestBody SendRequest request, BindingResult bindingResult) {
        requestLogger.log(request);
        List<ValidationMessage> modelValidationMessages = validationMessageMapper.map(bindingResult);
        
        if(!modelValidationMessages.isEmpty()) {
            return new ResponseEntity<List<ValidationMessage>>(modelValidationMessages, new HttpHeaders(), HttpStatus.BAD_REQUEST);
        }
        
        SendParameterSet parameterSet = createParameterSetMapper.map(request);
        Message message = Message.send(parameterSet);
        messageRepository.save(message);
        MessageDto messageDto = genericDtoMapper.map(message, MessageDto.class);
        return new ResponseEntity<MessageDto>(messageDto, new HttpHeaders(), HttpStatus.CREATED);
    }
    
    //TODO: ensure this is protected, that the user is allowed access to the conversation.
    @RequestMapping(value = "/reply-to", method = RequestMethod.PUT)
    @ResponseBody
    @Transactional
    public ResponseEntity<?> replyTo(@RequestBody ReplyToRequest request, BindingResult bindingResult) {
        requestLogger.log(request);
        List<ValidationMessage> modelValidationMessages = validationMessageMapper.map(bindingResult);
        
        if(!modelValidationMessages.isEmpty()) {
            return new ResponseEntity<List<ValidationMessage>>(modelValidationMessages, new HttpHeaders(), HttpStatus.BAD_REQUEST);
        }

        Message originalMessage = messageRepository.getById(request.getOriginalMessageId());
        ReplyToParameterSet parameterSet = replyParameterSetMapper.map(request);
        Message newMessage = originalMessage.replyTo(parameterSet);
        messageRepository.save(newMessage);
        MessageDto newMessageDto = genericDtoMapper.map(newMessage, MessageDto.class);
        return new ResponseEntity<MessageDto>(newMessageDto, new HttpHeaders(), HttpStatus.CREATED);
    }
}
