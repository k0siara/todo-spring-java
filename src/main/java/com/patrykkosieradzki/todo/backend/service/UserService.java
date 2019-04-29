package com.patrykkosieradzki.todo.backend.service;

import com.patrykkosieradzki.todo.AppConstants;
import com.patrykkosieradzki.todo.app.HasLogger;
import com.patrykkosieradzki.todo.backend.entity.ActivationToken;
import com.patrykkosieradzki.todo.backend.entity.User;
import com.patrykkosieradzki.todo.backend.mail.Email;
import com.patrykkosieradzki.todo.backend.repository.ActivationTokenRepository;
import com.patrykkosieradzki.todo.backend.repository.UserRepository;
import com.patrykkosieradzki.todo.backend.service.util.FieldValueExists;
import com.patrykkosieradzki.todo.ui.utils.ThymeleafUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service
public class UserService implements FieldValueExists, HasLogger {

    private UserRepository userRepository;
    private ActivationTokenRepository activationTokenRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EmailService emailService;

    @Autowired
    public UserService(UserRepository userRepository, ActivationTokenRepository activationTokenRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.activationTokenRepository = activationTokenRepository;
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

    public void register(User user) {
        ActivationToken activationToken = createActivationToken();
        user.setActivationToken(activationToken);

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);

        sendAccountActivationEmail(user);
    }

    public ActivationToken createActivationToken() {
        ActivationToken activationToken = ActivationToken.create();
        getLogger().debug("generated random activation token: " + activationToken);

        activationTokenRepository.save(activationToken);

        getLogger().debug("inserted new activation token with id: " + activationToken.getId());

        return activationTokenRepository.findById(activationToken.getId())
                .orElseThrow(() -> new RuntimeException("ActivationToken not found"));
    }

    private void sendAccountActivationEmail(User user) {
        String body = ThymeleafUtils.getProcessedHtml(Map.of(
                "activation_link", AppConstants.ACTIVATION_ADDRESS + user.getActivationToken().getValue()),
                "registration-email-template");

        emailService.sendMessage(new Email("todo.spring.java@outlook.com", user.getEmail(), AppConstants.ACTIVATION_EMAIL_SUBJECT, body, true));
    }

    public void save(User user) {
        userRepository.save(user);
    }

    public void update(User user) {
        userRepository.update(user);
    }

    @Override
    public boolean fieldValueExists(String fieldName, Object value) {
        return userRepository.existsByFieldName(fieldName, value.toString());
    }
}
