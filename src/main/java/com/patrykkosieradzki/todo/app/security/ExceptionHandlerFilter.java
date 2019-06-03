package com.patrykkosieradzki.todo.app.security;

import com.patrykkosieradzki.todo.api.exception.ApiError;
import com.patrykkosieradzki.todo.app.HasLogger;
import com.patrykkosieradzki.todo.app.StringUtils;
import com.patrykkosieradzki.todo.app.security.jwt.InvalidJwtAuthenticationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ExceptionHandlerFilter extends OncePerRequestFilter implements HasLogger {

    @Override
    public void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(req, res);
        } catch (InvalidJwtAuthenticationException e) {
            getLogger().debug("Handling InvalidJwtAuthenticationException...");

            res.setStatus(HttpStatus.UNAUTHORIZED.value());
            res.setContentType("application/json");
            res.getWriter().write(StringUtils.toJsonString(
                    new ApiError(HttpStatus.UNAUTHORIZED.value(), e.getLocalizedMessage())
            ));
        }
    }
}
