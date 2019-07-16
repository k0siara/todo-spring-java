package com.patrykkosieradzki.todo.ui;

import com.patrykkosieradzki.todo.app.security.CurrentUser;
import com.patrykkosieradzki.todo.app.security.SecurityUtils;
import com.patrykkosieradzki.todo.backend.entity.Todo;
import com.patrykkosieradzki.todo.backend.service.TodoService;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.spring.annotation.SpringComponent;
import lombok.Getter;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

@Getter
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class TodoList extends VerticalLayout implements TodoListener, TodoEditFormListener {

    private List<Todo> todos;

    private TodoEditForm todoEditForm;
    private Dialog dialog;

    private TodoService todoService;
    private CurrentUser currentUser;

    public TodoList(TodoService todoService, CurrentUser currentUser) {
        this.todoService = todoService;
        this.currentUser = currentUser;

        init();
    }

    private void init() {
        todos = new ArrayList<>();
        setWidth("60%");

        todoEditForm = new TodoEditForm(this);
        dialog = new Dialog();
        dialog.add(todoEditForm);

        update();
    }


    private void update() {
        removeAll();

        if (SecurityUtils.isUserLoggedIn()) {
            todos = todoService.findAllByUserUsername(currentUser.getUser().getUsername());
        }

        todos.forEach(todo -> add(new TodoLayout(todo, this)));
    }

    void addTodo(Todo todo) {
        if (!SecurityUtils.isUserLoggedIn()) {
            todo.setId((long) (todos.size() + 1));
            todo.setTimestamp(LocalDateTime.now());
        } else {
            todo.setUser(currentUser.getUser());
            todoService.save(todo);
        }

        todos.add(todo);
        update();
    }

    @Override
    public void onTodoChanged(Todo todo) {
        if (SecurityUtils.isUserLoggedIn()) {
            todoService.update(todo, todo.getId()); // TODO: 2019-07-16  
        } else {
            int index = IntStream.range(0, todos.size())
                    .filter(i -> todos.get(i).getId().equals(todo.getId()))
                    .findFirst()
                    .orElse(-1);

            todos.remove(index);
            todos.add(index, todo);
        }

        update();
    }

    @Override
    public void onTodoEditClick(Todo todo) {
        todoEditForm.setTodo(todo);
        dialog.setOpened(true);
    }

    @Override
    public void onSaveClick(Todo todo) {
        onTodoChanged(todo);
        dialog.setOpened(false);
    }

    public void deleteCompleted() {
        if (!SecurityUtils.isUserLoggedIn()) {
            todos.removeIf(Todo::isDone);
        } else {
            todoService.deleteByDone(currentUser.getUser(), true);
        }

        update();
    }

    @Override
    public void onDeleteClick(Todo todo) {
        if (!SecurityUtils.isUserLoggedIn()) {
            todos.remove(todo);
        } else {
            todoService.delete(todo);
        }

        dialog.setOpened(false);
        update();
    }

}
