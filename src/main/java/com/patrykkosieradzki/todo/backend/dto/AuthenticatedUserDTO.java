package com.patrykkosieradzki.todo.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticatedUserDTO extends AbstractDTO {

    private String firstName;
    private String lastName;
    private String username;
    private String email;

    private int todos;

    private boolean isExpired;
    private boolean isLocked;
    private boolean isCredentialsExpired;
    private boolean isEnabled;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
