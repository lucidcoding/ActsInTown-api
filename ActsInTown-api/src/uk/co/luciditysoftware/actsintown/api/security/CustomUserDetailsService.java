package uk.co.luciditysoftware.actsintown.api.security;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import uk.co.luciditysoftware.actsintown.domain.entities.User;
import uk.co.luciditysoftware.actsintown.domain.repositorycontracts.UserRepository;
 
//import com.kb.dao.UserDAOImpl;
//import com.kb.model.CustomUser;
 
//This is needed to handle refresh tokens.

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public UserPrincipal loadUserByUsername(String username) throws UsernameNotFoundException {
        
        //TODO: Refactor this into shared module along with equivalent in DatabaseAuthenticationProvider
        User user = userRepository.getByUsername(username);
        final UUID id = user.getId();
        
        List<CustomGrantedAuthority> authorities =  user.getRole().getRolePermissions().stream()
                .map(rolePermission -> new CustomGrantedAuthority() {
                    private static final long serialVersionUID = 1L;

                    {
                        setName(rolePermission.getPermission().getName());
                    }
                }).collect(Collectors.toList());
        
        UserPrincipal userPrincipal = new UserPrincipal() {
            private static final long serialVersionUID = 1L;

            {
                setId(id);
                setUsername(username);
                setAuthorities(authorities);
            }
        };
        
        return userPrincipal;
    }
}