package com.patrykkosieradzki.todo.backend.service;

import com.patrykkosieradzki.todo.backend.entity.User;
import com.patrykkosieradzki.todo.backend.repository.UserRepository;
import com.patrykkosieradzki.todo.backend.service.util.FieldValueExists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements FieldValueExists {

    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
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
