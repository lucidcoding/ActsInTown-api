package uk.co.luciditysoftware.actsintown.api.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import uk.co.luciditysoftware.actsintown.api.datatransferobjects.MessageDto;
import uk.co.luciditysoftware.actsintown.api.mappers.dtomappers.GenericDtoMapper;
import uk.co.luciditysoftware.actsintown.api.utilities.CurrentUserResolver;
import uk.co.luciditysoftware.actsintown.domain.entities.Message;
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
    private CurrentUserResolver currentUserResolver;
    
    @Autowired
    private GenericDtoMapper genericDtoMapper;
    
    @RequestMapping(value = "/for-conversation/{conversationId}/{page}/{pageSize}", method = RequestMethod.GET)
    @ResponseBody
    @Transactional
    public List<MessageDto> getForCurrentUser(@PathVariable UUID conversationId, @PathVariable int page, @PathVariable int pageSize) {
        String username = currentUserResolver.getUsername();
        
        List<Message> messages = messageRepository.getByConversationId(conversationId, page, pageSize);
        List<MessageDto> messageDtos = genericDtoMapper.map(messages, MessageDto.class);
        return messageDtos;
    }
}
