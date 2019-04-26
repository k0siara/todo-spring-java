package com.patrykkosieradzki.todo.backend.service;

import com.helger.commons.annotation.VisibleForTesting;
import com.patrykkosieradzki.todo.TodoAppConstants;
import com.patrykkosieradzki.todo.app.HasLogger;
import com.patrykkosieradzki.todo.backend.entity.ActivationToken;
import com.patrykkosieradzki.todo.backend.entity.User;
import com.patrykkosieradzki.todo.backend.mail.CustomEmailService;
import com.patrykkosieradzki.todo.backend.mail.Email;
import com.patrykkosieradzki.todo.backend.repository.ActivationTokenRepository;
import com.patrykkosieradzki.todo.backend.repository.UserRepository;
import com.patrykkosieradzki.todo.backend.service.util.FieldValueExists;
import com.patrykkosieradzki.todo.backend.util.ServerUtils;
import com.patrykkosieradzki.todo.backend.util.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.time.LocalDateTime;
import java.util.List;


@Service
public class UserService implements FieldValueExists, HasLogger {

    private UserRepository userRepository;
    private ActivationTokenRepository activationTokenRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    private CustomEmailService emailService;

    @Autowired
    private TemplateEngine templateEngine; // TODO: 2019-04-23 remove field injections

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
        ActivationToken activationToken = new ActivationToken(TokenUtils.getRandomToken());
        getLogger().debug("generated random activation token: " + activationToken);

        activationToken.setExpiresAt(LocalDateTime.now().plusDays(7));
        activationTokenRepository.save(activationToken);

        getLogger().debug("inserted new activation token with id: " + activationToken.getId());

        return activationTokenRepository.findById(activationToken.getId())
                .orElseThrow(() -> new RuntimeException("ActivationToken not found"));
    }

    private void sendAccountActivationEmail(User user) {
        Context context = new Context();
        context.setVariable("activation_link",
                ServerUtils.getAddress() + TodoAppConstants.ACTIVATION_ENDPOINT + user.getActivationToken().getValue());

        String body = templateEngine.process("registration-email-template", context);
        emailService.sendMessage(new Email("todo.spring.java@outlook.com", user.getEmail(), TodoAppConstants.ACTIVATION_EMAIL_SUBJECT, body, true));
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
