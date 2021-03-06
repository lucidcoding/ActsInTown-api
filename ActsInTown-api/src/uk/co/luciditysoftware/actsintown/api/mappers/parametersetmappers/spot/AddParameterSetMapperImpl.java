package uk.co.luciditysoftware.actsintown.api.mappers.parametersetmappers.spot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import uk.co.luciditysoftware.actsintown.api.requests.spot.AddRequest;
import uk.co.luciditysoftware.actsintown.domain.parametersets.spot.AddParameterSet;
import uk.co.luciditysoftware.actsintown.domain.repositorycontracts.TownRepository;
import uk.co.luciditysoftware.actsintown.domain.repositorycontracts.UserRepository;

@Service
public class AddParameterSetMapperImpl implements AddParameterSetMapper {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TownRepository townRepository;

    public AddParameterSet map(AddRequest request) {
        String username = (String) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();

        AddParameterSet parameterSet = new AddParameterSet() {
            {
                this.setUser(userRepository.getByUsername(username));
                this.setScheduledFor(request.getScheduledFor());
                this.setDurationMinutes(request.getDurationMinutes());
                this.setTown(townRepository.getById(request.getTownId()));
                this.setVenueName(request.getVenueName());
                this.setDescription(request.getDescription());
                this.setBookedState(request.getBookedState());
            }
        };

        return parameterSet;
    }
}
