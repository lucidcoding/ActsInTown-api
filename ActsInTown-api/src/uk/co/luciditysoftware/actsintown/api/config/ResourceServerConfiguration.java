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

    //@Autowired
    //private AuthenticationEntryPoint authenticationEntryPoint;
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
     * For creating bean resolver for messageAccessCheck.
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

            //.exceptionHandling().authenticationEntryPoint(authenticationEntryPoint).and()
            .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/county").permitAll()
                .antMatchers(HttpMethod.GET, "/test").permitAll()
                .antMatchers(HttpMethod.GET, "/usertype").permitAll()
                .antMatchers(HttpMethod.GET, "/user/verify").permitAll()
                .antMatchers(HttpMethod.POST, "/user/register").permitAll()
                .antMatchers(HttpMethod.PUT, "/user/initialize-password-reset").permitAll()
                .antMatchers(HttpMethod.PUT, "/user/reset-password").permitAll()
                .antMatchers(HttpMethod.GET, "/spot/search").permitAll()
                .antMatchers(HttpMethod.GET, "/town").permitAll()
                .antMatchers(HttpMethod.GET, "/town/for-county/{countyId}").permitAll()
                .antMatchers(HttpMethod.GET, "/spot/for-test-user").permitAll()
                .antMatchers(HttpMethod.POST, "/spot/for-test-user").permitAll()
                .antMatchers(HttpMethod.GET, "/chat").permitAll()
                .antMatchers(HttpMethod.GET, "/chat/info").permitAll()
                .antMatchers(HttpMethod.GET, "/message/{id}/{thing}").access("@accessChecker.check(authentication,request,#id,#thing)")//.access("#thing != 'OK'")//.access("hasAuthority('MakeBookingy')")//.hasAuthority("MakeBooking")
                
                .anyRequest().authenticated()

                .and()
            .addFilterBefore(new CorsFilter(), ChannelProcessingFilter.class); //Doesn't handle /oauth/token - this is handled in Bootstrap.
                //.and()
    	    //.exceptionHandling().authenticationEntryPoint(Http401AuthenticationEntryPoint("headerValue"));
            //.exceptionHandling().accessDeniedHandler(new CustomAccessDeniedHandler());
    }
}
