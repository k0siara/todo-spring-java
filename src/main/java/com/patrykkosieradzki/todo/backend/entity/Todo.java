package com.patrykkosieradzki.todo.backend.entity;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
public class Todo extends AbstractEntity {

    @NonNull
    private String text;
    private boolean isDone = false;
    private LocalDateTime timestamp;

    private Long userId;

    public Todo(Long id, String text) {
        super(id);
        this.text = text;
    }
}
