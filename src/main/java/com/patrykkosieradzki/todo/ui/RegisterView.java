package com.patrykkosieradzki.todo.ui;

import com.patrykkosieradzki.todo.TodoAppConstants;
import com.patrykkosieradzki.todo.backend.entity.User;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.page.Viewport;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.Validator;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;


@Route
@PageTitle("Register")
@Viewport(TodoAppConstants.VIEWPORT)
public class RegisterView extends VerticalLayout {

    private final BeanValidationBinder<User> binder;
    private User user;

    public RegisterView() {
        binder = new BeanValidationBinder<>(User.class);
        binder.setBean(user);

        TextField firstNameField = new TextField("First Name");
        Binder.Binding<User, String> firstNameBinding = binder.forField(firstNameField).bind("firstName");
        firstNameField.addValueChangeListener(event -> firstNameBinding.validate());
        firstNameField.setAutoselect(true);
        firstNameField.setAutofocus(true);

        TextField lastNameField = new TextField("Last Name");
        lastNameField.addValueChangeListener(event -> binder.validate());
        lastNameField.setAutoselect(true);
        binder.forField(lastNameField).bind("lastName");

        TextField usernameField = new TextField("Username");
        usernameField.addValueChangeListener(event -> binder.validate()); // TODO: 17-Apr-19 username taken live preview
        usernameField.setAutoselect(true);
        binder.forField(usernameField).bind("username");

        PasswordField passwordField = new PasswordField("Password");
        passwordField.addValueChangeListener(event -> binder.validate());
        passwordField.setAutoselect(true);
        binder.forField(passwordField).bind("password");

        PasswordField confirmPasswordField = new PasswordField("Confirm password");
        confirmPasswordField.setAutoselect(true);
        binder.forField(confirmPasswordField).withValidator(Validator.from((s -> s.equals(passwordField.getValue())), "Passwords don't match"));


        Button registerButton = new Button("Sign Up");
        registerButton.addClickListener(event -> {
            Notification.show("kliknięto");
            binder.validate();
        });

        add(firstNameField, lastNameField, usernameField, passwordField, confirmPasswordField);
        add(registerButton);
    }

}
