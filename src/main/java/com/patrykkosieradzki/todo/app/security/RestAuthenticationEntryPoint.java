package com.patrykkosieradzki.todo.app.security;


import com.patrykkosieradzki.todo.api.exception.ApiError;
import com.patrykkosieradzki.todo.app.HasLogger;
import com.patrykkosieradzki.todo.app.StringUtils;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public final class RestAuthenticationEntryPoint implements AuthenticationEntryPoint, HasLogger {

    @Override
    public void commence(
            final HttpServletRequest req,
            final HttpServletResponse res,
            final AuthenticationException ex) throws IOException {

        //response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");

        getLogger().debug(ex.getLocalizedMessage());

        res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        res.setContentType("application/json");
        res.getWriter().write(StringUtils.toJsonString(new ApiError(
                HttpServletResponse.SC_UNAUTHORIZED,
                "Unauthorized"
        )));
    }

}