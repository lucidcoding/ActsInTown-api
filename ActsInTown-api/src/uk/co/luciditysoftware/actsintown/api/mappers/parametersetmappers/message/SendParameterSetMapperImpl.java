package uk.co.luciditysoftware.actsintown.api.mappers.parametersetmappers.message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uk.co.luciditysoftware.actsintown.api.requests.message.SendRequest;
import uk.co.luciditysoftware.actsintown.api.utilities.CurrentUserResolver;
import uk.co.luciditysoftware.actsintown.domain.parametersets.message.SendParameterSet;
import uk.co.luciditysoftware.actsintown.domain.repositorycontracts.UserRepository;

@Service
public class SendParameterSetMapperImpl implements SendParameterSetMapper {

    @Autowired
    private CurrentUserResolver currentUserResolver;

    @Autowired
    private UserRepository userRepository;
    
    public SendParameterSet map(SendRequest request) {
        SendParameterSet parameterSet = new SendParameterSet() {
            {
                this.setSender(currentUserResolver.getUser());
                this.setRecipient(userRepository.getById(request.getRecipientId()));
                this.setBody(request.getBody());
                this.setTitle(request.getTitle());
            }
        };
        
        return parameterSet;
    }
}
