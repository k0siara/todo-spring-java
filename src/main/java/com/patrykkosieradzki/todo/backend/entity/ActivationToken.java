package com.patrykkosieradzki.todo.backend.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ActivationToken{

    private String value;

    private LocalDateTime expiresAt;

    private Long id;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

}


