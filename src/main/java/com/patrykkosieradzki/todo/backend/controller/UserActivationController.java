package com.patrykkosieradzki.todo.backend.controller;

import com.patrykkosieradzki.todo.backend.entity.User;
import com.patrykkosieradzki.todo.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserActivationController {

    private UserService userService;

    @Autowired
    public UserActivationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/activate")
    public String activateAccount(@RequestParam("token") String token) {
        User user = userService.findByActivationToken(token);

        if (user.isEnabled()) {
            return "user already active";
        } else {
            userService.enable(user);

            return "user activated, you can log in now";
        }
    }
}
