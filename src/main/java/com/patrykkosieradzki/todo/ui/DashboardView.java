package com.patrykkosieradzki.todo.ui;

import com.patrykkosieradzki.todo.backend.entity.Todo;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.html.Main;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

@HtmlImport("styles/shared-styles.html")
@Route(value = "dashboard", layout = MainView.class)
@RouteAlias(value = "", layout = MainView.class)
@PageTitle("Dashboard Todo app")
public class DashboardView extends VerticalLayout {

    private TodoList todoList = new TodoList();

    public DashboardView() {
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

        TextField taskField = new TextField();
        Button addButton = new Button("Add");

        addButton.addClickListener(event -> {
            if (taskField.isEmpty()) {
                Notification.show("Task cannot be empty");

            } else {
                todoList.addTodo(new Todo(taskField.getValue(), false));
                taskField.setValue("");
                taskField.focus();
            }

        });

        formLayout.add(taskField, addButton);
        formLayout.expand(taskField);

        add(formLayout);

        add(todoList);

        Button deleteButton = new Button("Delete completed");
        deleteButton.getStyle().set("color", "green");
        add(deleteButton);

        deleteButton.addClickListener(event -> {
            todoList.deleteCompleted();
        });

        addButton.addClickShortcut(Key.ENTER);
        taskField.focus();
    }

    private void addTodoList() {
    }

    private void addActionButtons() {
    }


}
