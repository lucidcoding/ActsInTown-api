package uk.co.luciditysoftware.actsintown.api.mappers.parametersetmappers.conversation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uk.co.luciditysoftware.actsintown.api.requests.conversation.StartRequest;
import uk.co.luciditysoftware.actsintown.api.utilities.CurrentUserResolver;
import uk.co.luciditysoftware.actsintown.domain.parametersets.conversation.StartParameterSet;
import uk.co.luciditysoftware.actsintown.domain.repositorycontracts.UserRepository;

@Service
public class StartParameterSetMapperImpl implements StartParameterSetMapper {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CurrentUserResolver currentUserResolver;
    
    @Override
    public StartParameterSet map(StartRequest request) {
        StartParameterSet parameterSet = new StartParameterSet() {{
            this.setUserFrom(userRepository.getByUsername("paul_t_d+3@hotmail.com"));
            //this.setUserFrom(currentUserResolver.getUser());
            this.setUsersTo(userRepository.getByIds(request.getUsersToIds()));
            this.setMessageBody(request.getMessageBody());
        }};
        
        return parameterSet;
    }

}
