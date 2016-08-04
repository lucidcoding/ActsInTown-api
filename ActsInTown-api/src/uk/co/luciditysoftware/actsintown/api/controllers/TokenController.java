package uk.co.luciditysoftware.actsintown.api.controllers;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import uk.co.luciditysoftware.actsintown.api.config.SecurityConfiguration;

@RestController
@RequestMapping("/token")
public class TokenController {
	
	@RequestMapping(value = "", method = RequestMethod.GET)
	@ResponseBody
	public String get(@RequestParam("username") String username, @RequestParam("password") String password)  {

		if(username.equals("pauldavies") && password.equals("password1")) {

			String compactJws = Jwts.builder()
			  .setSubject(username)
			  .signWith(SignatureAlgorithm.HS512, SecurityConfiguration.JWT_SIGNING_KEY)
			  .compact();
			
			return compactJws;
		}
		else {
			throw new BadCredentialsException("Bad Credentials");
		}
	}
}
