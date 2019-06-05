package com.patrykkosieradzki.todo.api.exception;

import com.patrykkosieradzki.todo.app.HasLogger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static org.springframework.http.ResponseEntity.badRequest;
import static org.springframework.http.ResponseEntity.status;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler implements HasLogger {

    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        getLogger().debug("Handling HttpRequestMethodNotSupportedException...");

        return new ResponseEntity<>(
                new ApiError(HttpStatus.NOT_FOUND.value(), "Not found"),
                HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public String requestHandlingNoHandlerFound() {
        return "asdc";
    }


    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {

        getLogger().debug("Handling MethodArgumentNotValidException...");

        return badRequest().body("chujowe");
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status,
                                                                  WebRequest request) {

        getLogger().debug("Handling HttpMessageNotReadableException... {}", ex.getLocalizedMessage());
        return badRequest().body("chujowe");
    }

    @ExceptionHandler(value = AccessDeniedException.class)
    public ResponseEntity handleAccessDeniedException(Exception ex, WebRequest req) {
        getLogger().debug("Handling AccessDeniedException... {}", ex.getLocalizedMessage());

        return status(HttpStatus.UNAUTHORIZED).body(
                new ApiError(HttpStatus.UNAUTHORIZED.value(), "Requires authentication")
        );
    }

    @ExceptionHandler(value = UserNotFoundException.class)
    public ResponseEntity handleUserNotFoundException(UserNotFoundException ex, WebRequest req) {
        getLogger().debug("Handling UserNotFoundException... {}", ex.getLocalizedMessage());

        return status(HttpStatus.NOT_FOUND).body(
                new ApiError(HttpStatus.NOT_FOUND.value(), ex.getLocalizedMessage())
        );
    }


}
