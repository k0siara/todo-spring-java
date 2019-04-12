package com.patrykkosieradzki.todo.api.controller;

import com.patrykkosieradzki.todo.backend.entity.User;
import com.patrykkosieradzki.todo.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users/{id}")
    public User getAllUsers(@PathVariable Long id) {
        return userService.findById(id);
    }

}
