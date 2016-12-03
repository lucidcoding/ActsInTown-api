package uk.co.luciditysoftware.actsintown.api.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * Configuration that is designed to be handled before the usual SecurityConfiguration class. This allows
 * all OPTIONS requests to be allowed through so that pre-flight requests for CORS can function correctly.
 * @author Paul Davies
 */
@Order(-1)
@Configuration
public class AllowOptionsSecurityConfiguration extends WebSecurityConfigurerAdapter{
	
	/**
	 * Configures the system to allow CORS pre-flight OPTIONS requests.
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.requestMatchers().antMatchers(HttpMethod.OPTIONS, "/**").and()
			.csrf().disable()	
           	.authorizeRequests().anyRequest().permitAll();
	}
}
