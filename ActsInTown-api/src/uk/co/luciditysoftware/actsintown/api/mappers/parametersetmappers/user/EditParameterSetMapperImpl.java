package uk.co.luciditysoftware.actsintown.api.mappers.parametersetmappers.user;

import java.security.NoSuchAlgorithmException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uk.co.luciditysoftware.actsintown.api.requests.user.EditCurrentRequest;
import uk.co.luciditysoftware.actsintown.domain.parametersets.user.EditParameterSet;
import uk.co.luciditysoftware.actsintown.domain.repositorycontracts.UserTypeRepository;

@Service
public class EditParameterSetMapperImpl implements EditParameterSetMapper{

    @Autowired
    private UserTypeRepository userTypeRepository;
    
    public EditParameterSet map(EditCurrentRequest request) throws NoSuchAlgorithmException {
        EditParameterSet parameterSet = new EditParameterSet() {
            {
                this.setFirstName(request.getFirstName());
                this.setLastName(request.getLastName());
                this.setUserTypes(userTypeRepository.getByIds(request.getUserTypeIds()));
                this.setStageName(request.getStageName());
            }
        };
        
        return parameterSet;
    }
}
