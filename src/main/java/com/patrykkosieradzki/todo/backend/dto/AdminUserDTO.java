package com.patrykkosieradzki.todo.backend.dto;

import com.patrykkosieradzki.todo.backend.entity.ActivationToken;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AdminUserDTO extends AbstractDTO {

    private String firstName;

    private String lastName;

    private String username;

    private String email;

    private int todos;

    private List<String> roles = new ArrayList<>();

    private boolean isExpired;
    private boolean isLocked;
    private boolean isCredentialsExpired;
    private boolean isEnabled;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private ActivationToken activationToken;

}
