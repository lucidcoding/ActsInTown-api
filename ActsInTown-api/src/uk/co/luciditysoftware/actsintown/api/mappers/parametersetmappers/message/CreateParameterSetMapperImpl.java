package uk.co.luciditysoftware.actsintown.api.mappers.parametersetmappers.message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uk.co.luciditysoftware.actsintown.api.requests.message.CreateRequest;
import uk.co.luciditysoftware.actsintown.api.utilities.CurrentUserResolver;
import uk.co.luciditysoftware.actsintown.domain.parametersets.message.CreateParameterSet;
import uk.co.luciditysoftware.actsintown.domain.repositorycontracts.ConversationRepository;
import uk.co.luciditysoftware.actsintown.domain.repositorycontracts.UserRepository;

@Service
public class CreateParameterSetMapperImpl implements CreateParameterSetMapper {
 
    @Autowired
    private ConversationRepository conversationRepository;
    
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CurrentUserResolver currentUserResolver;
    
    public CreateParameterSet map(CreateRequest request) {
        CreateParameterSet parameterSet = new CreateParameterSet() {
            {
                this.setUser(currentUserResolver.getUser());
                this.setUser(userRepository.getByUsername("paul_t_d+3@hotmail.com"));
                this.setConversation(conversationRepository.getById(request.getConversationId()));
                this.setBody(request.getBody());
            }
        };
        
        return parameterSet;
    }
}
