package com.patrykkosieradzki.todo.ui;

import com.patrykkosieradzki.todo.backend.entity.Todo;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@UIScope
@SpringComponent
public class TodoList extends VerticalLayout implements TodoChangeListener {

    private List<Todo> todos;

    public TodoList() {
        todos = new ArrayList<>();

        setWidth("60%");
    }

    private void update() {
        removeAll();
        todos.forEach(todo -> add(new TodoLayout(todo, this)));
    }

    void addTodo(Todo todo) {
        //repository.save(todo);
        todos.add(todo);
        update();
    }

    @Override
    public void todoChanged(Todo todo) {
        addTodo(todo);
    }


    public void deleteCompleted() {
        //repository.deleteByDone(true);

        todos = todos
                .stream()
                .filter(todo -> !todo.isDone())
                .collect(Collectors.toList());

        update();
    }
}
