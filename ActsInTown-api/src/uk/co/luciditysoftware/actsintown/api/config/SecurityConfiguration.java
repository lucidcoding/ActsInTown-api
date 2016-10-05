package uk.co.luciditysoftware.actsintown.api.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

//http://technicalrex.com/2015/02/20/stateless-authentication-with-spring-security-and-jwt
//https://github.com/Robbert1/boot-stateless-auth/blob/master/src/main/java/com/jdriven/stateless/security/StatelessAuthenticationSecurityConfig.java
//http://blog.cleveranalytics.com/post/106512852494/implementing-token-based-authentication-in-a

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	
	public static final String JWT_SIGNING_KEY = "hd6620asj#d9/dw";

    @Autowired
    private UnauthorizedAuthenticationEntryPoint authenticationEntryPoint;
    
    @Autowired
    private JwtAutenticationFilter jwtAutenticationFilter;
    
    @Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.exceptionHandling().authenticationEntryPoint(authenticationEntryPoint).and()
			//.anonymous().and()
			//.servletApi().and()
			.authorizeRequests()
			.antMatchers("/usertype").permitAll()
			.antMatchers("/town").permitAll()
			.antMatchers("/token").permitAll()
			.antMatchers(HttpMethod.POST, "/user/register").permitAll()
			.antMatchers(HttpMethod.GET, "/user/verify").permitAll()
			.antMatchers(HttpMethod.OPTIONS, "/user/register").permitAll()
			.antMatchers(HttpMethod.OPTIONS, "/spot").permitAll()
			.antMatchers(HttpMethod.OPTIONS, "/spot/for-current-user").permitAll()
			.anyRequest().authenticated().and()
			.headers().cacheControl().disable().and()
			.csrf().disable()
			.addFilterBefore(jwtAutenticationFilter, UsernamePasswordAuthenticationFilter.class);
	}
}