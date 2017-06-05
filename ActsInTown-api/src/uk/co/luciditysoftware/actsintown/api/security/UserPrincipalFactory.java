package uk.co.luciditysoftware.actsintown.api.security;

import java.util.List;

import uk.co.luciditysoftware.actsintown.domain.entities.User;

public interface UserPrincipalFactory {
    public List<CustomGrantedAuthority> getAuthorities(User user);
    public UserPrincipal getUserPrincipal(User user);
}
