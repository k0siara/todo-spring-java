package com.patrykkosieradzki.todo.api.controller;

import com.patrykkosieradzki.todo.app.security.CurrentUser;
import com.patrykkosieradzki.todo.backend.dto.AuthenticatedUserDTO;
import com.patrykkosieradzki.todo.backend.dto.UserDTO;
import com.patrykkosieradzki.todo.backend.mapper.UserMapper;
import com.patrykkosieradzki.todo.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {

    private UserService userService;
    private UserMapper userMapper;
    private CurrentUser currentUser;

    @Autowired
    public UserController(UserService userService, UserMapper userMapper, CurrentUser currentUser) {
        this.userService = userService;
        this.userMapper = userMapper;
        this.currentUser = currentUser;
    }

    @GetMapping("/user")
    public AuthenticatedUserDTO getCurrentUser() {
        return userMapper.toAuthenticatedUserDTO(currentUser.getUser());
    }

//    @PatchMapping("/user")
//    public UserDTO updateUser(@RequestBody UserDTO userDTO) {
//
//    }

    @GetMapping("/users")
    public List<UserDTO> getAllUsers() {
        return userMapper.toUserDTOs(userService.findAll());
    }

    @GetMapping("/users/{username}")
    public UserDTO getUserById(@PathVariable String username) {
        return userMapper.toUserDTOs(userService.findByUsername(username));
    }


}
