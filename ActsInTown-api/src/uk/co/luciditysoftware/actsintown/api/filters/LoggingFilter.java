package uk.co.luciditysoftware.actsintown.api.filters;

import java.io.IOException;

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
		log.debug(id + ": http request " + request.getRequestURI());
		super.doFilterInternal(requestLoggingWrapper, responseLoggingWrapper, filterChain);
		log.debug(id + ": http response " + response.getStatus() + " finished in " + (System.currentTimeMillis() - id)
				+ "ms");
	}

	@Override
	protected void beforeRequest(HttpServletRequest request, String message) {

	}

	@Override
	protected void afterRequest(HttpServletRequest request, String message) {

	}
}
