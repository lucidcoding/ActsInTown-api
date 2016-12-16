package uk.co.luciditysoftware.actsintown.api.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import uk.co.luciditysoftware.actsintown.api.filters.LoggingFilter;

@ControllerAdvice
public class GlobalControllerExceptionHandler extends ResponseEntityExceptionHandler {
    public static final String DEFAULT_ERROR_VIEW = "error";
    private static final Logger log = LogManager.getLogger(LoggingFilter.class);

    @ExceptionHandler(value = Exception.class)
    protected ResponseEntity<Object> handleConflict(Exception ex, WebRequest request) {
        StringBuilder stack = new StringBuilder(ex.toString() + System.lineSeparator());
        
        for (StackTraceElement stackTraceElement : ex.getStackTrace()) {
            stack.append("\t" + stackTraceElement.toString() + System.lineSeparator());
        }

        log.error(stack.toString()); 
        String bodyOfResponse = "Error";
        return handleExceptionInternal(ex, bodyOfResponse, 
          new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }
}
