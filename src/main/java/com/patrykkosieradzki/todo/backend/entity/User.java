package com.patrykkosieradzki.todo.backend.entity;

import com.patrykkosieradzki.todo.backend.service.UserService;
import com.patrykkosieradzki.todo.backend.validator.Unique;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User extends AbstractEntity {

    @NotEmpty(message = "First name can't be empty")
    private String firstName;

    @NotEmpty(message = "Last name can't be empty")
    private String lastName;

    @NotEmpty(message = "Username can't be empty")
    @Unique(service = UserService.class, fieldName = "username", message = "Username is already taken")
    private String username;

    @Email(message = "Email is invalid")
    @NotEmpty(message = "Email can't be empty")
    @Unique(service = UserService.class, fieldName = "email", message = "Email is already taken")
    private String email;

    @NotEmpty(message = "Password can't be empty") // TODO: 17-Apr-19 password validator
    private String password;

    private boolean isExpired;
    private boolean isLocked;
    private boolean isCredentialsExpired;
    private boolean isEnabled;

    private String activationToken;
}
