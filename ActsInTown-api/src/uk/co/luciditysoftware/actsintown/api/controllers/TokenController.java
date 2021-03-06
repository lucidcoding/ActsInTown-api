package uk.co.luciditysoftware.actsintown.api.controllers;

import java.nio.charset.StandardCharsets;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import uk.co.luciditysoftware.actsintown.api.config.SecurityConfiguration;
import uk.co.luciditysoftware.actsintown.domain.entities.User;
import uk.co.luciditysoftware.actsintown.domain.repositorycontracts.UserRepository;

@RestController
@RequestMapping("/token")
public class TokenController {
    
    @Autowired
    private UserRepository userRepository; 
    
    @RequestMapping(value = "", method = RequestMethod.GET)
    @ResponseBody
    @Transactional
    public ResponseEntity<?> get(@RequestParam("username") String username, @RequestParam("password") String password) {
        User user = userRepository.getByUsername(username);

        if (user == null || !user.isVerified()) {
            return new ResponseEntity<Void>(new HttpHeaders(), HttpStatus.UNAUTHORIZED);
        }

        byte[] passwordBytes = Base64.decodeBase64(user.getPassword());

        if (!BCrypt.checkpw( password, new String(passwordBytes, StandardCharsets.UTF_8) )) {
            return new ResponseEntity<Void>(new HttpHeaders(), HttpStatus.UNAUTHORIZED);
        }
        
        String compactJws = Jwts.builder()
            .setSubject(username)
            .signWith(SignatureAlgorithm.HS512, SecurityConfiguration.JWT_SIGNING_KEY)
            .compact();

        return new ResponseEntity<String>(compactJws, new HttpHeaders(), HttpStatus.OK);
    }
}
