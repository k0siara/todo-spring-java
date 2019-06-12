package com.patrykkosieradzki.todo.backend.service;

import com.patrykkosieradzki.todo.AppConstants;
import com.patrykkosieradzki.todo.api.exception.UserNotFoundException;
import com.patrykkosieradzki.todo.api.exception.UsernameTakenException;
import com.patrykkosieradzki.todo.app.HasLogger;
import com.patrykkosieradzki.todo.backend.dto.UserDTO;
import com.patrykkosieradzki.todo.backend.entity.ActivationToken;
import com.patrykkosieradzki.todo.backend.entity.User;
import com.patrykkosieradzki.todo.backend.mail.Email;
import com.patrykkosieradzki.todo.backend.repository.ActivationTokenRepository;
import com.patrykkosieradzki.todo.backend.repository.UserRepository;
import com.patrykkosieradzki.todo.backend.service.util.FieldValueExists;
import com.patrykkosieradzki.todo.ui.utils.ThymeleafUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
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


    public List<User> findAll(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found by id"));
    }

    public User findByActivationToken(String activationToken) {
        return userRepository.findByActivationToken(activationToken)
                .orElseThrow(() -> new UserNotFoundException("User not found by activationToken"));
    }

    public User register(User user) {
        ActivationToken activationToken = createActivationToken();
        user.setActivationToken(activationToken);

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);

        sendAccountActivationEmail(user);
        return user;
    }

    public ActivationToken createActivationToken() {
        ActivationToken activationToken = ActivationToken.create();
        getLogger().debug("generated random activation token: {}", activationToken);

        activationTokenRepository.save(activationToken);

        getLogger().debug("inserted new activation token with id: {}", activationToken.getId());

        return activationTokenRepository.findById(activationToken.getId())
                .orElseThrow(() -> new RuntimeException("ActivationToken not found"));
    }

    private void sendAccountActivationEmail(User user) {
        String body = ThymeleafUtils.getProcessedHtml(Map.of(
                "activation_link", AppConstants.ACTIVATION_ADDRESS + user.getActivationToken().getValue()),
                "registration-email-template");

        emailService.sendMessage(new Email("todo.spring.java@outlook.com", user.getEmail(), AppConstants.ACTIVATION_EMAIL_SUBJECT, body, true));
    }

    public User save(User user) {
        userRepository.save(user);
        return userRepository.findById(user.getId())
                .orElseThrow(() -> new UserNotFoundException("User not found by id"));
    }

    @PreAuthorize("isAuthenticated() and (hasRole('ROLE_ADMIN') or #username == authentication.principal.username)")
    public User update(UserDTO userDTO, String username) {
        User u = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User not found by username"));

        u.setFirstName(userDTO.getFirstName() != null ? userDTO.getFirstName() : u.getFirstName());
        u.setLastName(userDTO.getLastName() != null ? userDTO.getLastName() : u.getLastName());
        u.setEmail(userDTO.getEmail() != null ? userDTO.getEmail() : u.getEmail());

        if (userDTO.getUsername() != null) {
            if (fieldValueExists("username", userDTO.getUsername())) {
                throw new UsernameTakenException("Username " + userDTO.getUsername() + " is already taken");
            } else {
                u.setUsername(userDTO.getUsername());
            }
        }

        userRepository.update(u);
        return userRepository.findById(u.getId())
                .orElseThrow(() -> new UserNotFoundException("User not found by id"));
    }

    public User enable(User user) {
        user.setEnabled(true);

        userRepository.update(user);
        return userRepository.findById(user.getId())
                .orElseThrow(() -> new UserNotFoundException("User not found by id"));
    }

    @Override
    public boolean fieldValueExists(String fieldName, Object value) {
        return userRepository.existsByFieldName(fieldName, value.toString());
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
    }
}
