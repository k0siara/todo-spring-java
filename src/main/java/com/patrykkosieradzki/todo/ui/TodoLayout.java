package com.patrykkosieradzki.todo.ui;

import com.patrykkosieradzki.todo.backend.entity.Todo;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.value.ValueChangeMode;


public class TodoLayout extends HorizontalLayout {

    private final Checkbox isDone;
    private final TextField text;

    public TodoLayout(Todo todo, TodoChangeListener changeListener) {
        isDone = new Checkbox();
        text = new TextField();
        text.setValueChangeMode(ValueChangeMode.ON_BLUR);

        Binder<Todo> binder = new Binder<>(Todo.class);
        binder.bindInstanceFields(this);
        binder.setBean(todo);

        add(isDone, text);
        expand(text);

        binder.addValueChangeListener(event -> changeListener.todoChanged(todo));
    }
}
