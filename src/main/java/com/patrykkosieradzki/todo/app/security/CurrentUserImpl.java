package com.patrykkosieradzki.todo.app.security;

import com.patrykkosieradzki.todo.backend.entity.User;
import com.patrykkosieradzki.todo.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CurrentUserImpl implements CurrentUser {

    private UserRepository userRepository;

    @Autowired
    public CurrentUserImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User getUser() {
        String username = SecurityUtils.getUsername();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User Not found by username"));

        return user;
    }
}
