package com.patrykkosieradzki.todo.backend.service;

import com.patrykkosieradzki.todo.backend.entity.ActivationToken;
import com.patrykkosieradzki.todo.backend.entity.User;
import com.patrykkosieradzki.todo.backend.mail.CustomEmailService;
import com.patrykkosieradzki.todo.backend.repository.UserRepository;
import com.patrykkosieradzki.todo.backend.service.util.FieldValueExists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserService implements FieldValueExists {

    private UserRepository userRepository;
    private ActivationTokenService activationTokenService;
    private PasswordEncoder passwordEncoder;

    @Autowired
    private CustomEmailService emailService;

    @Autowired
    public UserService(UserRepository userRepository, ActivationTokenService activationTokenService, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.activationTokenService = activationTokenService;
        this.passwordEncoder = passwordEncoder;
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found by id"));
    }

    public User findByActivationToken(String activationToken) {
        return userRepository.findByActivationToken(activationToken)
                .orElseThrow(() -> new RuntimeException("User not found by activationToken"));
    }


    public User register(User user) {
        ActivationToken activationToken = activationTokenService.create();
        user.setActivationTokenId(activationToken.getId());

        LocalDateTime now = LocalDateTime.now();
        user.setCreatedAt(now);
        user.setUpdatedAt(now);

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        emailService.sendMessage(user.getEmail(), "link aktywacyjny", "twoj token aktywacyjny: " + activationToken.getValue());

        userRepository.save(user);
        return userRepository.findById(user.getId())
                .orElseThrow(() -> new RuntimeException("User not found"));
    }


    public void enable(User user) {
        userRepository.enable(user);
    }

    public void save(User user) {
        userRepository.save(user);
    }

    @Override
    public boolean fieldValueExists(String fieldName, Object value) {
        return userRepository.existsByFieldName(fieldName, value.toString());
    }
}
