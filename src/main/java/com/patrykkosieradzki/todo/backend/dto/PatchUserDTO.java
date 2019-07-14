package com.patrykkosieradzki.todo.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PatchUserDTO extends AbstractDTO {

    private String firstName;
    private String lastName;
    private String username;
    private String email;

    private Boolean isExpired;
    private Boolean isLocked;
    private Boolean isCredentialsExpired;
    private Boolean isEnabled;
}
