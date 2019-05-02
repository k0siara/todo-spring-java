package com.patrykkosieradzki.todo.backend.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Todo {

    private String text;
    private boolean isDone;

}
