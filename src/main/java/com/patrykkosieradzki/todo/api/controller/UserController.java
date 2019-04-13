package com.patrykkosieradzki.todo.api.controller;

import com.patrykkosieradzki.todo.backend.entity.User;
import com.patrykkosieradzki.todo.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userService.findAll();
    }

    @GetMapping("/users/{id}")
    public User getUserById(@PathVariable Long id) {
        return userService.findById(id);
    }

    @PostMapping("/users")
    public User addUser(@RequestBody @Valid User user) {
        userService.save(user);
        return user;
    }

}
