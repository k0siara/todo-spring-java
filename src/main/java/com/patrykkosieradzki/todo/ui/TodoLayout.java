package com.patrykkosieradzki.todo.ui;

import com.patrykkosieradzki.todo.backend.entity.Todo;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.value.ValueChangeMode;

public class TodoLayout extends HorizontalLayout {

    private final Checkbox isDone;
    private final TextField text;
    private final Button edit;

    public TodoLayout(Todo todo, TodoListener todoListener) {
        isDone = new Checkbox();

        text = new TextField();
        text.setValueChangeMode(ValueChangeMode.ON_BLUR);
        text.setReadOnly(true);

        edit = new Button(new Icon(VaadinIcon.EDIT), e -> todoListener.onTodoEditClick(todo));

        Binder<Todo> binder = new Binder<>(Todo.class);
        binder.bindInstanceFields(this);
        binder.setBean(todo);

        add(isDone, text, edit);
        expand(text);

        binder.addValueChangeListener(event -> todoListener.onTodoChanged(todo));
    }
}
