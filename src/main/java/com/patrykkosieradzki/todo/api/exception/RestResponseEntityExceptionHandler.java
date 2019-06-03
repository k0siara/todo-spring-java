package com.patrykkosieradzki.todo.api.exception;

import com.patrykkosieradzki.todo.app.HasLogger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static org.springframework.http.ResponseEntity.badRequest;
import static org.springframework.http.ResponseEntity.status;

@RestControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler implements HasLogger {

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

        getLogger().debug("Handling HttpMessageNotReadableException...");
        return badRequest().body("chujowe");
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity exception(Exception ex, WebRequest request) {
        System.out.println("DUPSKO");
        return status(HttpStatus.UNAUTHORIZED).build();
    }

}
