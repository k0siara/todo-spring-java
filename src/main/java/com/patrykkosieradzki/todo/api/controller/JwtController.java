package com.patrykkosieradzki.todo.api.controller;

import com.patrykkosieradzki.todo.api.entity.JwtAuthenticationRequest;
import com.patrykkosieradzki.todo.api.entity.JwtAuthenticationResponse;
import com.patrykkosieradzki.todo.app.security.jwt.JwtTokenProvider;
import com.patrykkosieradzki.todo.backend.entity.Role;
import com.patrykkosieradzki.todo.backend.entity.User;
import com.patrykkosieradzki.todo.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.ResponseEntity.ok;

@RestController
public class JwtController {

    private AuthenticationManager authenticationManager;
    private JwtTokenProvider jwtTokenProvider;
    private UserService userService;

    @Autowired
    public JwtController(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider,
                         UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
    }

    @PostMapping("/api/authorize")
    public ResponseEntity<JwtAuthenticationResponse> authorize(@RequestBody @Valid JwtAuthenticationRequest req) {
        String username = req.getUsername();
        String password = req.getPassword();

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));

        User user = userService.findByUsername(username);
        String token = jwtTokenProvider.createToken(
                username,
                user.getRoles().stream().map(Role::getName).collect(Collectors.toList())
        );

        return ok(new JwtAuthenticationResponse(username, token));
    }

}
