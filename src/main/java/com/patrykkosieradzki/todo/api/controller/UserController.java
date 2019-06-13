package com.patrykkosieradzki.todo.api.controller;

import com.patrykkosieradzki.todo.api.PageableDefaults;
import com.patrykkosieradzki.todo.api.entity.ApiResponse;
import com.patrykkosieradzki.todo.app.security.CurrentUser;
import com.patrykkosieradzki.todo.app.security.SecurityUtils;
import com.patrykkosieradzki.todo.app.security.annotations.IsAdminOrCurrentUser;
import com.patrykkosieradzki.todo.app.security.annotations.IsAuthenticated;
import com.patrykkosieradzki.todo.backend.dto.UserDTO;
import com.patrykkosieradzki.todo.backend.entity.User;
import com.patrykkosieradzki.todo.backend.mapper.UserMapper;
import com.patrykkosieradzki.todo.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.ResponseEntity.ok;

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
    public ResponseEntity<Object> getAllUsers(@PageableDefaults(minSize = 50, maxSize = 50, size = 50) Pageable pageable) {
        if (SecurityUtils.isUserLoggedIn() && currentUser.getUser().hasRole("ROLE_ADMIN")) {
            return ok().body(userMapper.toAdminUserDTO(userService.findAll(pageable)));
        } else {
            return ok().body(userMapper.toUserDTO(userService.findAllActive(pageable)));
        }
    }

    @GetMapping("/users/{username}")
    public ResponseEntity<Object> getUserByUsername(@PathVariable String username) {
        if (SecurityUtils.isUserLoggedIn() && currentUser.getUser().hasRole("ROLE_ADMIN")) {
            return ok().body(userMapper.toAdminUserDTO(userService.findByUsername(username)));
        } else {
            return ok().body(userMapper.toUserDTO(userService.findByUsername(username)));
        }
    }

    @IsAuthenticated
    @GetMapping("/user")
    public ResponseEntity<Object> getCurrentUser() {
        if (currentUser.getUser().hasRole("ROLE_ADMIN")) {
            return ok().body(userMapper.toAdminUserDTO(currentUser.getUser()));
        } else {
            return ok().body(userMapper.toAuthenticatedUserDTO(currentUser.getUser()));
        }
    }

    @IsAdminOrCurrentUser
    @PatchMapping("/users/{username}")
    public UserDTO updateUserByUsername(@RequestBody UserDTO userDTO, @PathVariable String username) {
        return userMapper.toUserDTO(userService.update(userDTO, username));
    }

    @IsAuthenticated
    @PatchMapping("/user")
    public UserDTO updateCurrentUser(@RequestBody UserDTO userDTO) {
        return userMapper.toUserDTO(userService.update(userDTO, currentUser.getUser().getUsername()));
    }

    @IsAdminOrCurrentUser
    @DeleteMapping("/users/{username}")
    public ResponseEntity<Object> deleteUserByUsername(@PathVariable String username) {
        User user = userService.findByUsername(username);
        userService.disable(user);

        return ok().body(new ApiResponse("User deleted"));
    }

    @IsAuthenticated
    @DeleteMapping("/user")
    public ResponseEntity<Object> deleteCurrentUser() {
        userService.disable(currentUser.getUser());

        return ok().body(new ApiResponse("User deleted"));
    }




}
