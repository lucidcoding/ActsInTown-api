package uk.co.luciditysoftware.actsintown.api.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import uk.co.luciditysoftware.actsintown.api.security.CustomTokenEnhancer;

/**
 * Configuration for the authorization server
 * @author Paul Davies
 * 
 * https://github.com/spring-projects/spring-security-oauth/issues/186
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {

    @Autowired
    @Qualifier("authenticationManagerBean")
    private AuthenticationManager authenticationManager;
    
    @Autowired
    private UserDetailsService userDetailsService;
 
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
            .withClient("my-trusted-client")
            .authorizedGrantTypes("password", "authorization_code", "refresh_token", "implicit")
            .authorities("ROLE_USER", "ROLE_CLIENT", "ROLE_TRUSTED_CLIENT")
            .scopes("read", "write", "trust")
            .accessTokenValiditySeconds(86400).//Access token is only valid for 1 day (in seconds).
            refreshTokenValiditySeconds(2592000);//Refresh token is only valid for 30 days.
    }
    
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints
            //.tokenServices(tokenServices())
            .tokenStore(tokenStore())
            .tokenEnhancer(tokenEnhancerChain())
            .accessTokenConverter(accessTokenConverter())
            .authenticationManager(authenticationManager)	//For handling of access tokens.
            .userDetailsService(userDetailsService);		//For handling of refresh tokens.
    }
    
    @Bean
    public TokenStore tokenStore() {
        return new JwtTokenStore(accessTokenConverter());
    }
 
    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setSigningKey(SecurityConfiguration.JWT_SIGNING_KEY);
        return converter;
    }
 
    /**
     * Needed?
     * @return
     *
    @Bean
    @Primary
    public DefaultTokenServices tokenServices() {
        DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
        defaultTokenServices.setTokenStore(tokenStore());
        defaultTokenServices.setTokenEnhancer(tokenEnhancerChain());
        defaultTokenServices.setSupportRefreshToken(true);
        return defaultTokenServices;
    }*/
    
    /*@Bean
    public JwtAccessTokenConverter tokenEnhancer(){
        final JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
        jwtAccessTokenConverter.setSigningKey("xxxx");
        return jwtAccessTokenConverter;
    }*/
    
    public TokenEnhancerChain tokenEnhancerChain(){
        final TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
        List<TokenEnhancer> tokenEnhancers = new ArrayList<TokenEnhancer>();
        tokenEnhancers.add(new CustomTokenEnhancer());
        tokenEnhancers.add(accessTokenConverter());
        tokenEnhancerChain.setTokenEnhancers(tokenEnhancers);
        return tokenEnhancerChain;
    }
}