package com.patrykkosieradzki.todo.ui;

import com.patrykkosieradzki.todo.backend.entity.Todo;

public interface TodoChangeListener {

    public void todoChanged(Todo todo);
}
