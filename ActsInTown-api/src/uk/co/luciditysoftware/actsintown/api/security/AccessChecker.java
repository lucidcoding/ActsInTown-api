package uk.co.luciditysoftware.actsintown.api.security;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;

import uk.co.luciditysoftware.actsintown.domain.entities.Message;
import uk.co.luciditysoftware.actsintown.domain.repositorycontracts.MessageRepository;

public class AccessChecker {

    @Autowired
    private MessageRepository messageRepository;

    @Transactional
    public boolean checkMessageAccess(Authentication authentication, UUID messageId) {
        String username = authentication.getName();
        Message message = messageRepository.getById(messageId);
        return message.getSender().getUsername().equals(username)
                || message.getRecipient().getUsername().equals(username);
    }

    @Transactional
    public boolean checkConversationAccess(Authentication authentication, UUID conversationId) {
        String username = authentication.getName();
        Message message = messageRepository.getLastByConversationId(conversationId);
        return message.getSender().getUsername().equals(username)
                || message.getRecipient().getUsername().equals(username);
    }
}
