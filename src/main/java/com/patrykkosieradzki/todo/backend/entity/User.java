package com.patrykkosieradzki.todo.backend.entity;

import com.patrykkosieradzki.todo.backend.service.UserService;
import com.patrykkosieradzki.todo.backend.validator.password.Password;
import com.patrykkosieradzki.todo.backend.validator.unique.Unique;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
public class User extends AbstractEntity {

    @NonNull
    @NotEmpty(message = "First name can't be empty")
    private String firstName;

    @NonNull
    @NotEmpty(message = "Last name can't be empty")
    private String lastName;

    @NonNull
    @NotEmpty(message = "Username can't be empty")
    @Unique(service = UserService.class, fieldName = "username", message = "Username is already taken")
    private String username;

    @NonNull
    @Email(message = "Email is invalid")
    @NotEmpty(message = "Email can't be empty")
    @Unique(service = UserService.class, fieldName = "email", message = "Email is already taken")
    private String email;

    @NonNull
    @Password(message = "Password has to be at least 8 characters long...") // TODO: 19-Apr-19 add better message
    @NotEmpty(message = "Password can't be empty")
    private String password;

    private boolean isExpired;
    private boolean isLocked;
    private boolean isCredentialsExpired;
    private boolean isEnabled;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private ActivationToken activationToken;
}
