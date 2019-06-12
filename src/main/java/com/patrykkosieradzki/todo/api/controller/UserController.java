package com.patrykkosieradzki.todo.api.controller;

import com.patrykkosieradzki.todo.api.PageableDefaults;
import com.patrykkosieradzki.todo.app.security.CurrentUser;
import com.patrykkosieradzki.todo.backend.dto.AuthenticatedUserDTO;
import com.patrykkosieradzki.todo.backend.dto.UserDTO;
import com.patrykkosieradzki.todo.backend.mapper.UserMapper;
import com.patrykkosieradzki.todo.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/users")
    public List<UserDTO> getAllUsers(@PageableDefaults(minSize = 50, maxSize = 50, size = 50) Pageable pageable) {
        return userMapper.toUserDTOs(userService.findAll(pageable));
    }

    @GetMapping("/users/{username}")
    public UserDTO getUserById(@PathVariable String username) {
        return userMapper.toUserDTO(userService.findByUsername(username));
    }

    @GetMapping("/user")
    public AuthenticatedUserDTO getCurrentUser() {
        return userMapper.toAuthenticatedUserDTO(currentUser.getUser());
    }

    @PatchMapping("/users/{username}")
    public UserDTO updateUserByUsername(@RequestBody UserDTO userDTO, @PathVariable String username) {
        return userMapper.toUserDTO(userService.update(userDTO, username));
    }

    @PatchMapping("/user")
    public UserDTO updateCurrentUser(@RequestBody UserDTO userDTO) {
        return userMapper.toUserDTO(userService.update(userDTO, currentUser.getUser().getUsername()));
    }




}
