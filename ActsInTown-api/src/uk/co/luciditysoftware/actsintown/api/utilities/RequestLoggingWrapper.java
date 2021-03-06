package uk.co.luciditysoftware.actsintown.api.utilities;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.apache.commons.io.input.TeeInputStream;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;

public class RequestLoggingWrapper extends HttpServletRequestWrapper {
    //private static final Logger log = LogManager.getLogger(RequestLoggingWrapper.class);
    private final ByteArrayOutputStream bos = new ByteArrayOutputStream();
    private long id;

    /**
     * @param requestId
     *            and id which gets logged to output file. It's used to bind
     *            request with response
     * @param request
     *            request from which we want to extract post data
     */
    public RequestLoggingWrapper(Long requestId, HttpServletRequest request) {
        super(request);
        this.id = requestId;
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        final ServletInputStream servletInputStream = RequestLoggingWrapper.super.getInputStream();
        return new ServletInputStream() {
            private TeeInputStream tee = new TeeInputStream(servletInputStream, bos);

            @Override
            public int read() throws IOException {
                return tee.read();
            }

            @Override
            public int read(byte[] b, int off, int len) throws IOException {
                return tee.read(b, off, len);
            }

            @Override
            public int read(byte[] b) throws IOException {
                return tee.read(b);
            }

            @Override
            public boolean isFinished() {
                return servletInputStream.isFinished();
            }

            @Override
            public boolean isReady() {
                return servletInputStream.isReady();
            }

            @Override
            public void setReadListener(ReadListener readListener) {
                servletInputStream.setReadListener(readListener);
            }

            @Override
            public void close() throws IOException {
                super.close();
                // do the logging
                logRequest();
            }
        };
    }

    public void logRequest() {
        //log.info(getId() + ": http request " + new String(toByteArray()));
    }

    public byte[] toByteArray() {
        return bos.toByteArray();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
    
    public String getRequestBody() throws IOException  {
        BufferedReader reader = new BufferedReader(new InputStreamReader(this.getInputStream()));
        String line = null;
        StringBuilder inputBuffer = new StringBuilder();
        do {
            line = reader.readLine();
            if (null != line) {
                inputBuffer.append(line.trim());
            }
        } while (line != null);
        reader.close();
        return inputBuffer.toString().trim();
    }
}
