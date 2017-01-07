package uk.co.luciditysoftware.actsintown.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;

import uk.co.luciditysoftware.actsintown.api.filters.CorsFilter;

@Configuration
@EnableResourceServer
public class ResourceServerConfiguration  extends ResourceServerConfigurerAdapter {

    //@Autowired
    //private AuthenticationEntryPoint authenticationEntryPoint;
    
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

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        final DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
        defaultTokenServices.setTokenStore(tokenStore());
        resources.tokenServices(defaultTokenServices);
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
                .antMatchers(HttpMethod.POST, "/conversation").permitAll()
                .anyRequest().authenticated()

                .and()
            .addFilterBefore(new CorsFilter(), ChannelProcessingFilter.class); //Doesn't handle /oauth/token - this is handled in Bootstrap.
                //.and()
    	    //.exceptionHandling().authenticationEntryPoint(Http401AuthenticationEntryPoint("headerValue"));
            //.exceptionHandling().accessDeniedHandler(new CustomAccessDeniedHandler());
    }
}
