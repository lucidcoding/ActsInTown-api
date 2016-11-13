package uk.co.luciditysoftware.actsintown.api.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

//http://technicalrex.com/2015/02/20/stateless-authentication-with-spring-security-and-jwt
//https://github.com/Robbert1/boot-stateless-auth/blob/master/src/main/java/com/jdriven/stateless/security/StatelessAuthenticationSecurityConfig.java
//http://blog.cleveranalytics.com/post/106512852494/implementing-token-based-authentication-in-a
//http://websystique.com/spring-security/secure-spring-rest-api-using-oauth2/

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	
	public static final String JWT_SIGNING_KEY = "hd6620asj#d9/dw";
     
	@Autowired
	private AuthenticationProvider authenticationProvider;
	
    @Autowired
    public void globalUserDetails(AuthenticationManagerBuilder auth) throws Exception {
    	auth.authenticationProvider(this.authenticationProvider);
    }
 
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
	        .csrf().disable()
	        .anonymous().disable()
	        .authorizeRequests().antMatchers(HttpMethod.POST, "/oauth/token").permitAll();
    }
 
    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}