package uk.co.luciditysoftware.actsintown.api.mappers.parametersetmappers.message;

import org.springframework.beans.factory.annotation.Autowired;

import uk.co.luciditysoftware.actsintown.api.requests.message.ReplyRequest;
import uk.co.luciditysoftware.actsintown.api.utilities.CurrentUserResolver;
import uk.co.luciditysoftware.actsintown.domain.parametersets.message.ReplyParameterSet;
import uk.co.luciditysoftware.actsintown.domain.repositorycontracts.MessageRepository;

public class ReplyParameterSetMapperImpl {

    @Autowired
    private CurrentUserResolver currentUserResolver;

    @Autowired
    private MessageRepository messageRepository;
    
    public ReplyParameterSet map(ReplyRequest request) {
        ReplyParameterSet parameterSet = new ReplyParameterSet() {
            {
                this.setSender(currentUserResolver.getUser());
                this.setOriginalMessage(messageRepository.getById(request.getOriginalMessageId()));
                this.setBody(request.getBody());
            }
        };
        
        return parameterSet;
    }
}
