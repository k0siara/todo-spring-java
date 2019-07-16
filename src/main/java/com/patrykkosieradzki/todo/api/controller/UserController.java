package com.patrykkosieradzki.todo.api.controller;

import com.patrykkosieradzki.todo.api.PageableDefaults;
import com.patrykkosieradzki.todo.api.entity.ApiResponse;
import com.patrykkosieradzki.todo.app.security.CurrentUser;
import com.patrykkosieradzki.todo.app.security.SecurityUtils;
import com.patrykkosieradzki.todo.app.security.annotations.IsAdminOrCurrentUser;
import com.patrykkosieradzki.todo.app.security.annotations.IsAuthenticated;
import com.patrykkosieradzki.todo.backend.dto.PatchUserDTO;
import com.patrykkosieradzki.todo.backend.dto.UserDTO;
import com.patrykkosieradzki.todo.backend.entity.User;
import com.patrykkosieradzki.todo.backend.mapper.UserMapper;
import com.patrykkosieradzki.todo.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    public UserDTO updateUserByUsername(@RequestBody PatchUserDTO patchUserDTO, @PathVariable String username) {
        User user = userService.findByUsername(username);

        user.setFirstName(patchUserDTO.getFirstName() != null ? patchUserDTO.getFirstName() : user.getFirstName());
        user.setLastName(patchUserDTO.getLastName() != null ? patchUserDTO.getLastName() : user.getLastName());
        user.setEmail(patchUserDTO.getEmail() != null ? patchUserDTO.getEmail() : user.getEmail());

        if (currentUser.getUser() != null && currentUser.getUser().hasRole("ROLE_ADMIN")) {
            user.setExpired(patchUserDTO.getIsExpired() != null ? patchUserDTO.getIsExpired() : user.isExpired());
            user.setLocked(patchUserDTO.getIsLocked() != null ? patchUserDTO.getIsLocked() : user.isLocked());
            user.setCredentialsExpired(patchUserDTO.getIsCredentialsExpired() != null ? patchUserDTO.getIsCredentialsExpired() : user.isCredentialsExpired());
            user.setEnabled(patchUserDTO.getIsEnabled() != null ? patchUserDTO.getIsEnabled() : user.isEnabled());
        }

        return userMapper.toUserDTO(userService.update(user));
    }

    @IsAuthenticated
    @PatchMapping("/user")
    public UserDTO updateCurrentUser(@RequestBody UserDTO userDTO) {
        User user = currentUser.getUser();

        user.setFirstName(userDTO.getFirstName() != null ? userDTO.getFirstName() : user.getFirstName());
        user.setLastName(userDTO.getLastName() != null ? userDTO.getLastName() : user.getLastName());
        user.setEmail(userDTO.getEmail() != null ? userDTO.getEmail() : user.getEmail());

        return userMapper.toUserDTO(userService.update(user));
    }

    @IsAdminOrCurrentUser
    @DeleteMapping("/users/{username}")
    public ResponseEntity<Object> deleteUserByUsername(@PathVariable String username) {
        User user = userService.findByUsername(username);
        userService.disable(user);

        return ok().body(new ApiResponse("User deleted"));
    }

    @DeleteMapping("/users/{username}")
    @PreAuthorize("isAuthenticated() && (hasRole('ROLE_ADMIN') or #username == authentication.principal.username)")
    public ResponseEntity<Object> deleteByUsername(@PathVariable String username) {
        User user = userService.findByUsername(username);

        user.setEnabled(false);
        userService.update(user);

        return ok(new ApiResponse("User deleted"));
    }

    @IsAuthenticated
    @DeleteMapping("/user")
    public ResponseEntity<Object> deleteCurrentUser() {
        User user = currentUser.getUser();

        user.setEnabled(false);
        userService.update(user);

        return ok(new ApiResponse("User deleted"));
    }


}
