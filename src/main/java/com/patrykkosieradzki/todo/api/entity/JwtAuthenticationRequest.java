package com.patrykkosieradzki.todo.api.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@AllArgsConstructor
public class JwtAuthenticationRequest implements Serializable {

    @NotNull
    private String username;

    @NotNull
    private String password;

}
