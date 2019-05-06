package com.patrykkosieradzki.todo.ui;

import com.patrykkosieradzki.todo.backend.entity.Todo;

public interface TodoListener {

    void onTodoChanged(Todo todo);
    void onTodoEditClick(Todo todo);
}
