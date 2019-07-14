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
public class TodoDTO extends AbstractDTO {

    private String text;
    private Boolean isDone;
    private LocalDateTime timestamp;
    private String user;
}
