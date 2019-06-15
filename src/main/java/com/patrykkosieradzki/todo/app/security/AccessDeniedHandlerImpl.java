package com.patrykkosieradzki.todo.app.security;

import com.patrykkosieradzki.todo.api.exception.ApiError;
import com.patrykkosieradzki.todo.app.HasLogger;
import com.patrykkosieradzki.todo.app.StringUtils;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AccessDeniedHandlerImpl implements AccessDeniedHandler, HasLogger {

    @Override
    public void handle(final HttpServletRequest request, final HttpServletResponse response,
                       final AccessDeniedException ex) throws IOException, ServletException {

        getLogger().debug(ex.getLocalizedMessage());

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        response.getWriter().write(StringUtils.toJsonString(new ApiError(
                HttpServletResponse.SC_FORBIDDEN,
                "Forbidden"
        )));
    }

}
