package com.patrykkosieradzki.todo.api.exception;

import com.patrykkosieradzki.todo.app.HasLogger;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ErrorControllerImpl implements ErrorController, HasLogger {

    @RequestMapping("/error")
    @ResponseBody
    public ResponseEntity<Object> handleError(HttpServletRequest request) {
        getLogger().debug("Handling URLNotFoundException...");

        return new ResponseEntity<>(
                new ApiError(HttpStatus.NOT_FOUND.value(), "Not found"),
                HttpStatus.NOT_FOUND
        );
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }
}
