package uk.co.luciditysoftware.actsintown.api.config;


import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;

public class SecurityBootstrap extends AbstractSecurityWebApplicationInitializer
{
    @Override
    protected boolean enableHttpSessionEventPublisher()
    {
        return true;
    }
}
