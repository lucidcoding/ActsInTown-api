package uk.co.luciditysoftware.actsintown.api.utilities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import uk.co.luciditysoftware.actsintown.api.filters.LoggingFilter;

@Service
public class RequestLoggerImpl implements RequestLogger {

    private static final Logger log = LogManager.getLogger(LoggingFilter.class);
    
    public void log(Object request) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            String requestJson;
            requestJson = mapper.writeValueAsString(request);
            log.debug(requestJson);
        } catch (JsonProcessingException e) {}
    }
}
