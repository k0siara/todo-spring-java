package com.patrykkosieradzki.todo.api.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JwtTokenResponse {

    String username;
    String token;

}
