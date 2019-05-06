package com.patrykkosieradzki.todo.ui;

import com.patrykkosieradzki.todo.backend.entity.Todo;

public interface TodoEditFormListener {

    void onSaveClick(Todo todo);
    void onDeleteClick(Todo todo);

}
