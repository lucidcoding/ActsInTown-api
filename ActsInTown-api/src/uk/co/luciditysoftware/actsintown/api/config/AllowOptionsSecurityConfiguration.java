package uk.co.luciditysoftware.actsintown.api.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@Order(-1)
@Configuration
public class AllowOptionsSecurityConfiguration extends WebSecurityConfigurerAdapter{
	@Override
	protected void configure(HttpSecurity http) throws Exception {
	   http
		   //.requestMatchers().antMatchers(HttpMethod.OPTIONS, "/oauth/token", "/spot/for-current-user")
		   .requestMatchers().antMatchers(HttpMethod.OPTIONS, "/**")
		   .and()
           .csrf().disable()	
           .authorizeRequests().anyRequest().permitAll();
		
		/*http
			.requestMatchers().antMatchers(HttpMethod.OPTIONS, "/oauth/token", "/spot/**")
        	.and()
            .csrf().disable()	
            .authorizeRequests().anyRequest().permitAll()
            .and()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);*/
	}
}
