package com.patrykkosieradzki.todo.app.security;

import com.patrykkosieradzki.todo.api.exception.UserNotFoundException;
import com.patrykkosieradzki.todo.app.HasLogger;
import com.patrykkosieradzki.todo.backend.entity.User;
import com.patrykkosieradzki.todo.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CurrentUserImpl implements CurrentUser, HasLogger {

    private UserRepository userRepository;

    @Autowired
    public CurrentUserImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User getUser() {
        getLogger().debug("Getting currently authenticated user");

        String username = SecurityUtils.getUsername();
        getLogger().debug("Currently logger in username: {}", username);

        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("Current user not found"));
    }
}
