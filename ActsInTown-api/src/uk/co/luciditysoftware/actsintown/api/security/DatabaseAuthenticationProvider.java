package uk.co.luciditysoftware.actsintown.api.security;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
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
    
    // http://javainsimpleway.com/spring/spring-security-using-custom-authentication-provider/

    @Override
    @Transactional
    public Authentication authenticate(Authentication authentication) {
        UsernamePasswordAuthenticationToken credentials = (UsernamePasswordAuthenticationToken) authentication;
        String username = credentials.getPrincipal().toString();
        String password = credentials.getCredentials().toString();
        credentials.eraseCredentials();

        /*
         * UserPrincipal principal =
         * this.userRepository.getByUsername(username); if(principal == null) {
         * log.warn("Authentication failed for non-existent user {}.",
         * username); return null; }
         * 
         * if(!BCrypt.checkpw( password, new String(principal.getPassword(),
         * StandardCharsets.UTF_8) )) { log.warn(
         * "Authentication failed for user {}.", username); return null; }
         * 
         * principal.setAuthenticated(true); log.debug(
         * "User {} successfully authenticated.", username);
         * 
         * return principal;
         */
        
        //byte[] decryptedPasswordBytes = Base64.decodeBase64(password);
        
        //String salt = BCrypt.gensalt(AuthenticationServiceImpl.HASHING_ROUNDS, AuthenticationServiceImpl.RANDOM);

        User user = null;
        try {
            user = userRepository.getByUsername(username);

            if (user == null) {
                return null;
            }
            
            byte[] passwordBytes = Base64.decodeBase64(user.getPassword());
    
            if (BCrypt.checkpw(password, new String(passwordBytes, StandardCharsets.UTF_8) )) {
                
                //TODO: Refactor this into shared module along with equivalent in CustomUserDetailsServer
                final String currentUsername = user.getUsername();
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
                        setUsername(currentUsername);
                        setAuthorities(authorities);
                    }
                };

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
