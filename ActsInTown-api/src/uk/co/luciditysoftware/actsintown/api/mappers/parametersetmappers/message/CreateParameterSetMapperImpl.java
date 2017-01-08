package uk.co.luciditysoftware.actsintown.api.mappers.parametersetmappers.message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uk.co.luciditysoftware.actsintown.api.requests.message.CreateRequest;
import uk.co.luciditysoftware.actsintown.api.utilities.CurrentUserResolver;
import uk.co.luciditysoftware.actsintown.domain.parametersets.message.CreateParameterSet;
import uk.co.luciditysoftware.actsintown.domain.repositorycontracts.ConversationRepository;

@Service
public class CreateParameterSetMapperImpl implements CreateParameterSetMapper {
 
    @Autowired
    private ConversationRepository conversationRepository;

    @Autowired
    private CurrentUserResolver currentUserResolver;
    
    public CreateParameterSet map(CreateRequest request) {
        CreateParameterSet parameterSet = new CreateParameterSet() {
            {
                this.setUser(currentUserResolver.getUser());
                this.setConversation(conversationRepository.getById(request.getConversationId()));
                this.setBody(request.getBody());
            }
        };
        
        return parameterSet;
    }
}
