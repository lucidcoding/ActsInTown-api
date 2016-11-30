package uk.co.luciditysoftware.actsintown.api.filters;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.AbstractRequestLoggingFilter;

import uk.co.luciditysoftware.actsintown.api.utilities.RequestLoggingWrapper;
import uk.co.luciditysoftware.actsintown.api.utilities.ResponseLoggingWrapper;

//http://stackoverflow.com/questions/30942818/logging-all-network-traffic-in-spring-mvc
//https://gist.github.com/calo81/2071634
//http://wetfeetblog.com/servlet-filer-to-log-request-and-response-details-and-payload/431

@Component("loggingFilter")
public class LoggingFilter extends AbstractRequestLoggingFilter {

    private static final Logger log = LogManager.getLogger(LoggingFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        long id = System.currentTimeMillis();
        RequestLoggingWrapper requestLoggingWrapper = new RequestLoggingWrapper(id, request);
        ResponseLoggingWrapper responseLoggingWrapper = new ResponseLoggingWrapper(id, response);
        Map<String, String> requestMap = this.getTypesafeRequestMap(request);
        
        final StringBuilder requestMessage = new StringBuilder("REST Request (" + id + ") - ")
                .append("[URL:")
                .append(request.getRequestURL())
                .append("] [URI:")
                .append(request.getRequestURI())
                .append("] [HTTP METHOD:")
                .append(request.getMethod())                                        
                .append("] [PATH INFO:")
                .append(request.getPathInfo())                                        
                .append("] [REQUEST PARAMETERS:")
                .append(requestMap)
                //.append("] [REQUEST BODY:")
                //.append(requestLoggingWrapper.getRequestBody())                                        
                .append("] [REMOTE ADDRESS:")
                .append(request.getRemoteAddr())
                .append("]");
        
        log.debug(requestMessage);
        super.doFilterInternal(requestLoggingWrapper, responseLoggingWrapper, filterChain);
        
        final StringBuilder responseMessage = new StringBuilder("REST Response (" + id + ") - ")
                .append("[STATUS:")
                .append(response.getStatus())
                .append("] [TIME:")
                .append(System.currentTimeMillis() - id)
                .append("ms]");
        
        log.debug(responseMessage);
    }

    private Map<String, String> getTypesafeRequestMap(HttpServletRequest request) {
        Map<String, String> typesafeRequestMap = new HashMap<String, String>();
        Enumeration<?> requestParamNames = request.getParameterNames();
        while (requestParamNames.hasMoreElements()) {
            String requestParamName = (String)requestParamNames.nextElement();
            String requestParamValue = request.getParameter(requestParamName);
            typesafeRequestMap.put(requestParamName, requestParamValue);
        }
        return typesafeRequestMap;
    }
    
    @Override
    protected void beforeRequest(HttpServletRequest request, String message) {

    }

    @Override
    protected void afterRequest(HttpServletRequest request, String message) {

    }
}
