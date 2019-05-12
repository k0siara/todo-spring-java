package com.patrykkosieradzki.todo.backend.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TodoDTO extends AbstractDTO {

    private String text;
    private boolean isDone;
    private LocalDateTime timestamp;
    private Long userId;
}
