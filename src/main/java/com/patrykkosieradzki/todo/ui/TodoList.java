package com.patrykkosieradzki.todo.ui;

import com.patrykkosieradzki.todo.app.security.CurrentUser;
import com.patrykkosieradzki.todo.app.security.SecurityUtils;
import com.patrykkosieradzki.todo.backend.entity.Todo;
import com.patrykkosieradzki.todo.backend.service.TodoService;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.spring.annotation.SpringComponent;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
            todos = todoService.findAllByUser(currentUser.getUser());
        }

        todos.forEach(todo -> add(new TodoLayout(todo, this)));
    }

    void addTodo(Todo todo) {
        if (!SecurityUtils.isUserLoggedIn()) {
            todo.setId(Long.valueOf(todos.size() + 1));
            todo.setTimestamp(LocalDateTime.now());
        } else {
            todo.setUserId(currentUser.getUser().getId());
            todoService.save(todo);
        }

        todos.add(todo);
        update();
    }

    @Override
    public void onTodoChanged(Todo todo) {
        Todo t = todos.stream().filter(t1 -> t1.getId().equals(todo.getId())).findFirst().get();
        t.setText(todo.getText());
        t.setDone(todo.isDone());
    }

    @Override
    public void onTodoEditClick(Todo todo) {
        todoEditForm.setTodo(todo);
        dialog.setOpened(true);
    }

    public void deleteCompleted() {
        if (!SecurityUtils.isUserLoggedIn()) {
            todos.removeIf(Todo::isDone);
        }

        //repository.deleteByDone(true);

        update();
    }

    @Override
    public void onSaveClick(Todo todo) {

    }

    @Override
    public void onDeleteClick(Todo todo) {
        dialog.setOpened(false);

        todos.remove(todo);

        update();
    }
}
