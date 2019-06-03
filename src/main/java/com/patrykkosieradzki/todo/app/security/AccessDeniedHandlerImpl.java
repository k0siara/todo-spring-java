package com.patrykkosieradzki.todo.app.security;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AccessDeniedHandlerImpl implements AccessDeniedHandler {

    @Override
    public void handle(final HttpServletRequest request, final HttpServletResponse response,
                       final AccessDeniedException ex) throws IOException, ServletException {
        response.getOutputStream().print("Error Message Goes Here");
        response.setStatus(403);
        // response.sendRedirect("/my-error-page");
    }

}
