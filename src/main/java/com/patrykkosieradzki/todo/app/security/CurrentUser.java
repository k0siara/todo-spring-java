package com.patrykkosieradzki.todo.app.security;

import com.patrykkosieradzki.todo.backend.entity.User;
import org.springframework.security.access.prepost.PreAuthorize;

@FunctionalInterface
public interface CurrentUser {

    @PreAuthorize("isAuthenticated()")
    User getUser();
}
