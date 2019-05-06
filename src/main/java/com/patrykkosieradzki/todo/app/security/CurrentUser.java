package com.patrykkosieradzki.todo.app.security;

import com.patrykkosieradzki.todo.backend.entity.User;

@FunctionalInterface
public interface CurrentUser {
    User getUser();
}
