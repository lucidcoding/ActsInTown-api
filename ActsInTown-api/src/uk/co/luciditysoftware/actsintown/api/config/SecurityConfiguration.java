package uk.co.luciditysoftware.actsintown.api.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.approval.ApprovalStore;
import org.springframework.security.oauth2.provider.approval.TokenApprovalStore;
import org.springframework.security.oauth2.provider.approval.TokenStoreUserApprovalHandler;
import org.springframework.security.oauth2.provider.request.DefaultOAuth2RequestFactory;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;

//http://technicalrex.com/2015/02/20/stateless-authentication-with-spring-security-and-jwt
//https://github.com/Robbert1/boot-stateless-auth/blob/master/src/main/java/com/jdriven/stateless/security/StatelessAuthenticationSecurityConfig.java
//http://blog.cleveranalytics.com/post/106512852494/implementing-token-based-authentication-in-a
//http://websystique.com/spring-security/secure-spring-rest-api-using-oauth2/

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	
	public static final String JWT_SIGNING_KEY = "hd6620asj#d9/dw";

    /*@Autowired
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
			.antMatchers(HttpMethod.GET, "/spot/search").permitAll()
			.antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
			.anyRequest().authenticated().and()
			.headers().cacheControl().disable().and()
			.csrf().disable()
			.addFilterBefore(jwtAutenticationFilter, UsernamePasswordAuthenticationFilter.class);
	}*/
	
	@Autowired
    private ClientDetailsService clientDetailsService;
     
	@Autowired
	private AuthenticationService authenticationService;
	
    @Autowired
    public void globalUserDetails(AuthenticationManagerBuilder auth) throws Exception {
    	auth.authenticationProvider(this.authenticationService);
    }
 
    /*@Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(this.authenticationService);
    }*/
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        /*http.addFilterBefore(new CorsFilter(), ChannelProcessingFilter.class);
        
        http
	        .requestMatcher(new AntPathRequestMatcher("/oauth/**"))
	        .csrf().disable()
	        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

	    http
	    	.authorizeRequests()
	    	.antMatchers("/spot/**").anonymous()
	        .and()
	        .csrf().disable()
	        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

	    http.authorizeRequests().anyRequest().permitAll();*/
    
        http
        	//.addFilterBefore(new CorsFilter(), ChannelProcessingFilter.class)
	        .csrf().disable()
	        .anonymous().disable()
	        .authorizeRequests()
	        .antMatchers(HttpMethod.GET, "/test").permitAll()
	        .antMatchers(HttpMethod.POST, "/oauth/token").permitAll()
			.antMatchers(HttpMethod.OPTIONS, "/**").permitAll();
    }
 
    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
 
 
    /*@Bean
    public TokenStore tokenStore() {
        return new InMemoryTokenStore();
    }
 
    @Bean
    @Autowired
    public TokenStoreUserApprovalHandler userApprovalHandler(TokenStore tokenStore){
        TokenStoreUserApprovalHandler handler = new TokenStoreUserApprovalHandler();
        handler.setTokenStore(tokenStore);
        handler.setRequestFactory(new DefaultOAuth2RequestFactory(clientDetailsService));
        handler.setClientDetailsService(clientDetailsService);
        return handler;
    }
     
    @Bean
    @Autowired
    public ApprovalStore approvalStore(TokenStore tokenStore) throws Exception {
        TokenApprovalStore store = new TokenApprovalStore();
        store.setTokenStore(tokenStore);
        return store;
    }*/
}