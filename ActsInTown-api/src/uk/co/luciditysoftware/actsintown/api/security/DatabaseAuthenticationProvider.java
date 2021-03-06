package uk.co.luciditysoftware.actsintown.api.security;

import java.nio.charset.StandardCharsets;
import java.util.List;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import uk.co.luciditysoftware.actsintown.domain.entities.User;
import uk.co.luciditysoftware.actsintown.domain.repositorycontracts.UserRepository;

//This is needed to handle access tokens
@Service
public class DatabaseAuthenticationProvider implements AuthenticationProvider {
    
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserPrincipalFactory userPrincipalFactory;
    
    // http://javainsimpleway.com/spring/spring-security-using-custom-authentication-provider/

    @Override
    @Transactional
    public Authentication authenticate(Authentication authentication) {
        UsernamePasswordAuthenticationToken credentials = (UsernamePasswordAuthenticationToken) authentication;
        String username = credentials.getPrincipal().toString();
        String password = credentials.getCredentials().toString();
        credentials.eraseCredentials();

        User user = null;
        try {
            user = userRepository.getByUsername(username);

            if (user == null) {
                return null;
            }
            
            byte[] passwordBytes = Base64.decodeBase64(user.getPassword());
    
            if (BCrypt.checkpw(password, new String(passwordBytes, StandardCharsets.UTF_8) )) {
                List<CustomGrantedAuthority> authorities = userPrincipalFactory.getAuthorities(user);
                UserPrincipal userPrincipal = userPrincipalFactory.getUserPrincipal(user);
                return new UsernamePasswordAuthenticationToken(userPrincipal, password, authorities);
            } else {
                return null;
            }
            
        } catch (Exception ex) {
            throw ex;
        }
    }

    @Override
    public boolean supports(Class<?> c) {
        return c == UsernamePasswordAuthenticationToken.class;
    }
}
