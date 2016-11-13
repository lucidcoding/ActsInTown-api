package uk.co.luciditysoftware.actsintown.api.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Order(-1)
@Configuration
public class AllowOptionsSecurityConfiguration extends WebSecurityConfigurerAdapter{
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.requestMatchers().antMatchers(HttpMethod.OPTIONS, "/**").and()
			.csrf().disable()	
           	.authorizeRequests().anyRequest().permitAll();
	}
}