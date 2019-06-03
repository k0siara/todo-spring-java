package com.patrykkosieradzki.todo.app.security;

import com.patrykkosieradzki.todo.backend.entity.User;
import com.patrykkosieradzki.todo.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

    private UserRepository userRepository;

    @Autowired
    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) {
        Optional<User> optionalUserByUsername = userRepository.findByUsername(usernameOrEmail);
        Optional<User> optionalUserByEmail = userRepository.findByEmail(usernameOrEmail);

        if (optionalUserByUsername.isEmpty() && optionalUserByEmail.isEmpty()) {
            throw new UsernameNotFoundException("User not found");
        }

        return new UserDetailsImpl(
                optionalUserByUsername.orElseGet(optionalUserByEmail::get)
        );
    }

}
