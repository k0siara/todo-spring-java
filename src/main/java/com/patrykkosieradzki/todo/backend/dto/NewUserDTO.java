package com.patrykkosieradzki.todo.backend.dto;

import lombok.Data;

@Data
public class NewUserDTO extends AbstactDTO {

    private String firstName;

    private String lastName;

    private String username;

    private String email;
}
