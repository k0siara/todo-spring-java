package com.patrykkosieradzki.todo.backend.entity;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
public class Todo extends AbstractEntity {

    @NonNull
    private String text;

    private Boolean isDone = false;
    private LocalDateTime timestamp;

    private User user;

    public Todo(Long id, String text) {
        super(id);
        this.text = text;
    }

    public Boolean isDone() {
        return isDone;
    }

    public void setDone(Boolean done) {
        isDone = done;
    }
}
