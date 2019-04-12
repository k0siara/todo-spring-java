package com.patrykkosieradzki.todo.backend.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User extends AbstractEntity {

    private String firstName;

    private String lastName;

    private String username;

    private String password;

}
