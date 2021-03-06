package uk.co.luciditysoftware.actsintown.api.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.expression.OAuth2WebSecurityExpressionHandler;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;

import uk.co.luciditysoftware.actsintown.api.filters.CorsFilter;
import uk.co.luciditysoftware.actsintown.api.security.AccessChecker;

@Configuration
@EnableResourceServer
public class ResourceServerConfiguration  extends ResourceServerConfigurerAdapter {

    @Autowired
    private OAuth2WebSecurityExpressionHandler expressionHandler;
    
    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {
    	JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setSigningKey(SecurityConfiguration.JWT_SIGNING_KEY);
        return converter;
    }

    @Bean
    public TokenStore tokenStore() {
        return new JwtTokenStore(accessTokenConverter());
    }

    /**
     * For creating bean resolver for accessChecker.
     * @param applicationContext
     * @return
     */
    @Bean
    public OAuth2WebSecurityExpressionHandler oAuth2WebSecurityExpressionHandler(ApplicationContext applicationContext) {
        OAuth2WebSecurityExpressionHandler expressionHandler = new OAuth2WebSecurityExpressionHandler();
        expressionHandler.setApplicationContext(applicationContext);
        return expressionHandler;
    }
    
    @Bean
    public AccessChecker accessChecker() {
        return new AccessChecker();
    }
    
    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        final DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
        defaultTokenServices.setTokenStore(tokenStore());
        resources.tokenServices(defaultTokenServices);
        
        resources.expressionHandler(expressionHandler);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
    	http
            .csrf().disable()
            .anonymous().and()
            .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/county")
                    .permitAll()
                .antMatchers(HttpMethod.GET, "/message/{messageId}")
                    .access("hasAuthority('ViewMessage') and @accessChecker.checkMessageAccess(authentication,#messageId)")
                .antMatchers(HttpMethod.GET, "/message/inbox/count")
                    .hasAuthority("ViewMessage")
                .antMatchers(HttpMethod.GET, "/message/inbox/{page}/{pageSize}")
                    .hasAuthority("ViewMessage")
                .antMatchers(HttpMethod.GET, "/message/sent-items/count")
                    .hasAuthority("ViewMessage")
                .antMatchers(HttpMethod.GET, "/message/sent-items/{page}/{pageSize}")
                    .hasAuthority("ViewMessage")
                .antMatchers(HttpMethod.GET, "/message/for-conversation/{conversationId}/{before}/{page}/{pageSize}")
                    .access("hasAuthority('ViewMessage') and @accessChecker.checkConversationAccess(authentication,#conversationId)")
                .antMatchers(HttpMethod.POST, "/message")
                    .hasAuthority("AddMessage")
                .antMatchers(HttpMethod.PUT, "/message/reply-to")
                    .hasAuthority("AddMessage")
                .antMatchers(HttpMethod.GET, "/spot/for-current-user")
                    .hasAuthority("ViewSpot")
                .antMatchers(HttpMethod.GET, "/spot/search")
                    .hasAuthority("ViewSpot")
                .antMatchers(HttpMethod.GET, "/spot/enquire-about/{id}")
                    .hasAuthority("ViewSpot")
                .antMatchers(HttpMethod.POST, "/spot")
                    .hasAuthority("AddSpot")
                .antMatchers(HttpMethod.DELETE, "/spot")
                    .hasAuthority("DeleteSpot")
                .antMatchers(HttpMethod.GET, "/town")
                    .permitAll()
                .antMatchers(HttpMethod.GET, "/town/for-county/{countyId}")
                    .permitAll()
                .antMatchers(HttpMethod.POST, "/user/register")
                    .permitAll()
                .antMatchers(HttpMethod.GET, "/user/verify")
                    .permitAll()
                .antMatchers(HttpMethod.GET, "/user/edit-current")
                    .hasAuthority("EditUser")
                .antMatchers(HttpMethod.GET, "/user/get-current")
                    .hasAuthority("ViewUser")
                .antMatchers(HttpMethod.GET, "/user/echange-password")
                    .hasAuthority("EditUser")
                .antMatchers(HttpMethod.PUT, "/user/initialize-password-reset")
                    .permitAll()
                .antMatchers(HttpMethod.PUT, "/user/reset-password")
                    .permitAll()
                .antMatchers(HttpMethod.GET, "/user/by-ids")
                    .hasAuthority("ViewUser")
                .antMatchers(HttpMethod.GET, "/usertype")
                    .permitAll()
                .anyRequest().authenticated()
                .and()
            .addFilterBefore(new CorsFilter(), ChannelProcessingFilter.class); //Doesn't handle /oauth/token - this is handled in Bootstrap.
    }
}
