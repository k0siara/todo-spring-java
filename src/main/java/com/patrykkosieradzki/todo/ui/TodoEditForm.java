package com.patrykkosieradzki.todo.ui;

import com.patrykkosieradzki.todo.backend.entity.Todo;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.timepicker.TimePicker;
import com.vaadin.flow.data.binder.Binder;
import lombok.Getter;

public class TodoEditForm extends VerticalLayout {

    private Label id = new Label("");
    private TextField text = new TextField("Text");
    private DatePicker date = new DatePicker("Date");
    private TimePicker time = new TimePicker("Time");
    private Button save = new Button("Save");
    private Button delete = new Button("Delete");

    private Binder<Todo> binder = new Binder<>(Todo.class);

    @Getter
    private Todo todo = new Todo();

    private TodoEditFormListener todoEditFormListener;

    public TodoEditForm(TodoEditFormListener todoEditFormListener) {
        this.todoEditFormListener = todoEditFormListener;

        init();
    }

    private void init() {
        setWidth("100%");

        H2 title = new H2("Todo #123");
        add(title);

        HorizontalLayout body = new HorizontalLayout(text, date, time);
        add(body);

        HorizontalLayout buttons = new HorizontalLayout(save, delete);
        buttons.setWidth("100%");

        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttons.setVerticalComponentAlignment(Alignment.STRETCH, save, delete);
        add(buttons);


        binder.bindInstanceFields(this);
        binder.setBean(todo);

        save.addClickListener(event -> todoEditFormListener.onSaveClick(todo));
        delete.addClickListener(event -> todoEditFormListener.onDeleteClick(todo));
    }

    public void setTodo(Todo todo) {
        this.todo = todo;

        id.setText(String.valueOf(todo.getId()));
        text.setValue(todo.getText());
        date.setValue(todo.getTimestamp().toLocalDate());
        time.setValue(todo.getTimestamp().toLocalTime());
    }

}
