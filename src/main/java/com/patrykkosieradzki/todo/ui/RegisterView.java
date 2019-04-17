package com.patrykkosieradzki.todo.ui;

import com.patrykkosieradzki.todo.TodoAppConstants;
import com.patrykkosieradzki.todo.backend.entity.User;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.page.Viewport;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.Validator;
import com.vaadin.flow.data.validator.StringLengthValidator;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;


@Route
@PageTitle("Register")
@Viewport(TodoAppConstants.VIEWPORT)
public class RegisterView extends VerticalLayout {

    private final BeanValidationBinder<User> binder;
    private User user;

    Binder<User> binder2 = new Binder<>();

    public RegisterView() {
        binder = new BeanValidationBinder<>(User.class);
        binder.setBean(user);

        TextField firstName = new TextField("First Name");
        Binder.Binding<User, String> firstNameBinding = binder.forField(firstName).bind("firstName");
        firstName.addValueChangeListener(event -> firstNameBinding.validate());
        firstName.setAutoselect(true);
        firstName.setAutofocus(true);

        TextField lastName = new TextField("Last Name");
        Binder.Binding<User, String> lastNameBinding = binder.forField(lastName).bind("lastName");
        lastName.addValueChangeListener(event -> lastNameBinding.validate());
        lastName.setAutoselect(true);

        TextField username = new TextField("Username");
        Binder.Binding<User, String> usernameBinding = binder.forField(username).bind("username");
        username.addValueChangeListener(event -> usernameBinding.validate());
        username.setAutoselect(true);

        EmailField email = new EmailField("Email");
        Binder.Binding<User, String> emailBinding = binder.forField(email).bind("email");
        email.addValueChangeListener(event -> emailBinding.validate());

        PasswordField password = new PasswordField("Password");
        Binder.Binding<User, String> passwordBinding = binder.forField(password).bind("password");
        password.addValueChangeListener(event -> passwordBinding.validate());
        password.setAutoselect(true);

        PasswordField confirmPassword = new PasswordField("Confirm password");
        Binder<String> confirmPasswordBinder = new Binder<>();
        confirmPasswordBinder
                .forField(confirmPassword)
                .withValidator((s -> !s.equals(password.getValue())), "dupa")
                .withValidationStatusHandler(status -> confirmPassword.setErrorMessage(status.getMessage().orElse(""))); // TODO: 18-Apr-19 not working
        confirmPassword.addValueChangeListener(event -> confirmPasswordBinder.validate());
        confirmPassword.setAutoselect(true);

        Button registerButton = new Button("Sign Up");
        registerButton.addClickListener(event -> {
            Notification.show("kliknięto");
            binder.validate();
            confirmPasswordBinder.validate();
        });

        add(firstName, lastName, username, email, password, confirmPassword);
        add(registerButton);
    }

}
