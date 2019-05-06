package com.patrykkosieradzki.todo.ui;

import com.patrykkosieradzki.todo.AppConstants;
import com.patrykkosieradzki.todo.backend.entity.Todo;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.*;
import org.springframework.beans.factory.annotation.Autowired;

@HtmlImport("styles/shared-styles.html")
@Route(value = AppConstants.PAGE_DASHBOARD, layout = MainView.class)
@RouteAlias(value = "", layout = MainView.class)
@PageTitle("Dashboard Todo app")
public class DashboardView extends VerticalLayout implements HasUrlParameter<Long> {

    private TodoList todoList;


    @Autowired
    public DashboardView(TodoList todoList) {
        this.todoList = todoList;

        init();
    }

    private void init() {
        setupLayout();
        addHeader();
        addForm();
        addTodoList();
        addActionButtons();
    }

    private void setupLayout() {
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
    }

    private void addHeader() {
        Label header = new Label("TODO");
        add(header);
    }

    private void addForm() {
        HorizontalLayout formLayout = new HorizontalLayout();
        formLayout.setWidth("60%");
        add(formLayout);

        TextField taskField = new TextField();
        taskField.focus();

        Button addButton = new Button("Add");
        addButton.addClickShortcut(Key.ENTER);

        addButton.addClickListener(event -> {
            if (taskField.isEmpty()) {
                Notification.show("Task cannot be empty");

            } else {
                todoList.addTodo(new Todo(taskField.getValue()));
                taskField.setValue("");
                taskField.focus();
            }
        });

        formLayout.add(taskField, addButton);
        formLayout.expand(taskField);
    }

    private void addTodoList() {
        add(todoList);
    }

    private void addActionButtons() {
        Button deleteButton = new Button("Delete completed");
        deleteButton.getStyle().set("color", "green");
        add(deleteButton);

        deleteButton.addClickListener(event -> {
//            todoList.deleteCompleted();
        });
    }

    @Override
    public void setParameter(BeforeEvent event, @OptionalParameter Long todoId) {
        if (todoId != null) {
//            todoEditForm.setTodo(todoId);
//            dialog.setOpened(true);
        }
    }

//    @Override
//    public void onTodoChanged(Todo todo) {
//
//    }
//
//    @Override
//    public void onTodoEditClick(Todo todo) {
//        UI.getCurrent().navigate(AppConstants.PAGE_DASHBOARD + "/" + todo.getId());
//    }
}
