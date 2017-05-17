package uk.co.luciditysoftware.actsintown.api.mappers.parametersetmappers.message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uk.co.luciditysoftware.actsintown.api.requests.message.ReplyToRequest;
import uk.co.luciditysoftware.actsintown.api.utilities.CurrentUserResolver;
import uk.co.luciditysoftware.actsintown.domain.parametersets.message.ReplyToParameterSet;

@Service
public class ReplyToParameterSetMapperImpl implements ReplyToParameterSetMapper {

    @Autowired
    private CurrentUserResolver currentUserResolver;
    
    public ReplyToParameterSet map(ReplyToRequest request) {
        ReplyToParameterSet parameterSet = new ReplyToParameterSet() {
            {
                this.setSender(currentUserResolver.getUser());
                this.setBody(request.getBody());
            }
        };
        
        return parameterSet;
    }
}
