package uk.co.luciditysoftware.actsintown.api.utilities;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import uk.co.luciditysoftware.actsintown.domain.entities.User;
import uk.co.luciditysoftware.actsintown.domain.repositorycontracts.UserRepository;

@Service 
public class CurrentUserResolverImpl implements CurrentUserResolver {
    @Autowired
    private UserRepository userRepository;
    
    public String getUsername() {
        String username = (String) SecurityContextHolder
            .getContext()
            .getAuthentication()
            .getPrincipal();
        
        return username;
    }
    
    public User getUser() {
        String username = getUsername();
        User user = userRepository.getByUsername(username);
        return user;
    }
}
