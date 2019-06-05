package com.patrykkosieradzki.todo.app;

import lombok.Getter;
import org.springframework.http.HttpHeaders;

@Getter
public class URLNotFoundException extends RuntimeException {

    private final String httpMethod;
    private final String requestURL;
    private final HttpHeaders headers;

    public URLNotFoundException(String httpMethod, String requestURL, HttpHeaders headers) {
        super("URL not found for method " + httpMethod + " and request url " + requestURL);
        this.httpMethod = httpMethod;
        this.requestURL = requestURL;
        this.headers = headers;
    }

    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }
}
