package com.patrykkosieradzki.todo.api.controller;

import com.patrykkosieradzki.todo.api.PageableDefaults;
import com.patrykkosieradzki.todo.api.entity.ApiResponse;
import com.patrykkosieradzki.todo.app.security.CurrentUser;
import com.patrykkosieradzki.todo.backend.dto.AuthenticatedUserDTO;
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

import java.util.List;

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
    public List<UserDTO> getAllUsers(@PageableDefaults(minSize = 50, maxSize = 50, size = 50) Pageable pageable) {
        return userMapper.toUserDTOs(userService.findAll(pageable));
    }

    @GetMapping("/users/{username}")
    public UserDTO getUserById(@PathVariable String username) {
        return userMapper.toUserDTO(userService.findByUsername(username));
    }

    @GetMapping("/user")
    @PreAuthorize("isAuthenticated()")
    public AuthenticatedUserDTO getCurrentUser() {
        return userMapper.toAuthenticatedUserDTO(currentUser.getUser());
    }

    @PatchMapping("/users/{username}")
    @PreAuthorize("isAuthenticated() and (hasRole('ROLE_ADMIN') or #username == authentication.principal.username)")
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

    @PatchMapping("/user")
    @PreAuthorize("isAuthenticated()")
    public UserDTO updateCurrentUser(@RequestBody UserDTO userDTO) {
        User user = currentUser.getUser();

        user.setFirstName(userDTO.getFirstName() != null ? userDTO.getFirstName() : user.getFirstName());
        user.setLastName(userDTO.getLastName() != null ? userDTO.getLastName() : user.getLastName());
        user.setEmail(userDTO.getEmail() != null ? userDTO.getEmail() : user.getEmail());

        return userMapper.toUserDTO(userService.update(user));
    }

    @DeleteMapping("/users/{username}")
    @PreAuthorize("isAuthenticated() && (hasRole('ROLE_ADMIN') or #username == authentication.principal.username)")
    public ResponseEntity<Object> deleteCurrentUser(@PathVariable String username) {
        User user = userService.findByUsername(username);

        user.setEnabled(false);
        userService.update(user);

        return ok(new ApiResponse("User deleted"));
    }

    @DeleteMapping("/user")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Object> deleteCurrentUser() {
        User user = currentUser.getUser();

        user.setEnabled(false);
        userService.update(user);

        return ok(new ApiResponse("User deleted"));
    }


}
