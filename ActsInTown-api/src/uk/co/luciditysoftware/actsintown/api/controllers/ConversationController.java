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

import uk.co.luciditysoftware.actsintown.api.datatransferobjects.ConversationDto;
import uk.co.luciditysoftware.actsintown.api.mappers.dtomappers.GenericDtoMapper;
import uk.co.luciditysoftware.actsintown.api.utilities.CurrentUserResolver;
import uk.co.luciditysoftware.actsintown.domain.entities.Conversation;
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
    private GenericDtoMapper genericDtoMapper;
    
    @RequestMapping(value = "/for-current-user/{page}/{pageSize}", method = RequestMethod.GET)
    @ResponseBody
    @Transactional
    public List<ConversationDto> getForCurrentUser(@PathVariable int page, @PathVariable int pageSize) {
        String username = currentUserResolver.getUsername();
        List<Conversation> conversations = conversationRepository.getByUsername(username, page, pageSize);
        List<ConversationDto> conversationDtos = genericDtoMapper.map(conversations, ConversationDto.class);
        return conversationDtos;
    }
}
