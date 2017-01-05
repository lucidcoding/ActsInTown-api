package uk.co.luciditysoftware.actsintown.api.mappers.parametersetmappers.message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import uk.co.luciditysoftware.actsintown.api.requests.message.CreateRequest;
import uk.co.luciditysoftware.actsintown.domain.parametersets.message.CreateParameterSet;
import uk.co.luciditysoftware.actsintown.domain.repositorycontracts.ConversationRepository;
import uk.co.luciditysoftware.actsintown.domain.repositorycontracts.UserRepository;

@Service
public class CreateParameterSetMapperImpl implements CreateParameterSetMapper {

    @Autowired
    private UserRepository userRepository;
 
    @Autowired
    private ConversationRepository conversationRepository;
    
    public CreateParameterSet map(CreateRequest request) {
        String username = (String) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
        
        CreateParameterSet parameterSet = new CreateParameterSet() {
            {
                this.setUser(userRepository.getByUsername(username));
                this.setConversation(conversationRepository.getById(request.getConversationId()));
                this.setBody(request.getBody());
            }
        };
        
        return parameterSet;
    }
}
