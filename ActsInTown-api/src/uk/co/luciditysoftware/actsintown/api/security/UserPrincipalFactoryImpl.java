package uk.co.luciditysoftware.actsintown.api.security;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import uk.co.luciditysoftware.actsintown.domain.entities.User;

@Service 
public class UserPrincipalFactoryImpl implements UserPrincipalFactory {
    public List<CustomGrantedAuthority> getAuthorities(User user) {
        List<CustomGrantedAuthority> authorities =  user.getRole().getRolePermissions().stream()
                .map(rolePermission -> new CustomGrantedAuthority() {
                    private static final long serialVersionUID = 1L;

                    {
                        setName(rolePermission.getPermission().getName());
                    }
                }).collect(Collectors.toList());
        
        return authorities;
    }

    public UserPrincipal getUserPrincipal(User user) {
        List<CustomGrantedAuthority> authorities =  getAuthorities(user);
        
        UserPrincipal userPrincipal = new UserPrincipal() {
            private static final long serialVersionUID = 1L;
            {
                setId(user.getId());
                setUsername(user.getUsername());
                setAuthorities(authorities);
            }
        };
        
        
        return userPrincipal;
    }
}
