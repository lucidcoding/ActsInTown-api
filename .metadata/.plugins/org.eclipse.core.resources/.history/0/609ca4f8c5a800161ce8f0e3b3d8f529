package uk.co.luciditysoftware.actsintown.api.config;

import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.springframework.security.web.access.channel.ChannelProcessingFilter;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.DispatcherServlet;

public class Bootstrap  implements WebApplicationInitializer{
	
    @Override
    public void onStartup(ServletContext container) throws ServletException
    {
        container.getServletRegistration("default").addMapping("/resource/*");

        AnnotationConfigWebApplicationContext rootContext =
                new AnnotationConfigWebApplicationContext();
        rootContext.register(RootContextConfiguration.class);
        container.addListener(new ContextLoaderListener(rootContext));

        AnnotationConfigWebApplicationContext servletContext =
                new AnnotationConfigWebApplicationContext();
        servletContext.register(ServletContextConfiguration.class);
        ServletRegistration.Dynamic dispatcher = container.addServlet(
                "springDispatcher", new DispatcherServlet(servletContext)
        );
        dispatcher.setLoadOnStartup(1);
        dispatcher.addMapping("/");
       
        FilterRegistration.Dynamic loggingFilter = container.addFilter("loggingFilter", new DelegatingFilterProxy());
        loggingFilter.addMappingForUrlPatterns(null, true, "/*");

        //http.addFilterBefore(new CorsFilter(), ChannelProcessingFilter.class);
        FilterRegistration.Dynamic corsFilter = container.addFilter("corsFilter", new DelegatingFilterProxy());
        corsFilter.addMappingForUrlPatterns(null, true, "/*");
    }
}
