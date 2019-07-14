package com.patrykkosieradzki.todo.backend.entity;

import com.patrykkosieradzki.todo.backend.service.UserService;
import com.patrykkosieradzki.todo.backend.validator.password.Password;
import com.patrykkosieradzki.todo.backend.validator.unique.Unique;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
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
    @Password(message = "Password has to be at least 8 characters long, contain at least one uppercase character, " +
            "one lowercase character, one digit, one special character and no whitespaces")
    @NotEmpty(message = "Password can't be empty")
    private String password;

    private List<Role> roles = new ArrayList<>();

    private List<Todo> todos = new ArrayList<>();

    private boolean isExpired;
    private boolean isLocked;
    private boolean isCredentialsExpired;
    private boolean isEnabled;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private ActivationToken activationToken;

    public boolean hasRole(String role) {
        return roles.stream().anyMatch(r -> r.getName().equals(role));
    }

    @Override
    public String toString() {
        return "User{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", isExpired=" + isExpired +
                ", isLocked=" + isLocked +
                ", isCredentialsExpired=" + isCredentialsExpired +
                ", isEnabled=" + isEnabled +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", activationToken=" + activationToken +
                '}';
    }
}
