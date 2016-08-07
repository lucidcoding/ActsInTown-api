package uk.co.luciditysoftware.actsintown.api.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Controller;

@Configuration
@ComponentScan(basePackages = {
		"uk.co.luciditysoftware.actsintown.api.controllers",
		"uk.co.luciditysoftware.actsintown.api.config"
}, 
excludeFilters = @ComponentScan.Filter(Controller.class) )
//@EnableTransactionManagement
@Import({ SecurityConfiguration.class })
//@Import({ ServerSecurityConfig.class, AuthServerOAuth2Config.class })
public class RootContextConfiguration {

}
