package uk.co.luciditysoftware.actsintown.api.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

@Configuration
@EnableResourceServer
public class ResourceServerConfiguration  extends ResourceServerConfigurerAdapter {
    private final String RESOURCE_ID = "spring1";
    
    @Autowired
    private ClientDetailsService clientDetailsService;

    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {
    	JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setSigningKey("123");
        return converter;
    }

    @Bean
    public TokenStore tokenStore() {
        return new JwtTokenStore(accessTokenConverter());
    }

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {

        final DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
        defaultTokenServices.setTokenStore(tokenStore());
        //defaultTokenServices.setTokenEnhancer(accessTokenConverter());
        //defaultTokenServices.setClientDetailsService(clientDetailsService);
        //resources.resourceId(RESOURCE_ID).tokenServices(defaultTokenServices);
        resources.tokenServices(defaultTokenServices);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {

        /*http.authorizeRequests().antMatchers("/spot/**").permitAll()
                .and()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);*/
    	
    	http
        .csrf().disable()
        .anonymous().and()
        .authorizeRequests()
        //
        .antMatchers(HttpMethod.GET, "/test").permitAll()
        .anyRequest().authenticated()
    	.and().exceptionHandling().accessDeniedHandler(new OAuth2AccessDeniedHandler());
    	
//    	http.
//        anonymous().disable()
//        .requestMatchers().antMatchers("/user/**")
//        .and().authorizeRequests()
//        .antMatchers("/user/**").access("hasRole('ADMIN')")
//        .and().exceptionHandling().accessDeniedHandler(new OAuth2AccessDeniedHandler());

    }
}
