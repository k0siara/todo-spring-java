package com.patrykkosieradzki.todo.ui;

import com.patrykkosieradzki.todo.TodoAppConstants;
import com.patrykkosieradzki.todo.backend.entity.User;
import com.patrykkosieradzki.todo.backend.service.UserService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
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
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;


@Route
@PageTitle("Register")
@Viewport(TodoAppConstants.VIEWPORT)
public class RegisterView extends VerticalLayout {

    private UserService userService;

    private final Binder<User> binder = new BeanValidationBinder<>(User.class);

    private TextField firstName = new TextField("First Name");
    private TextField lastName = new TextField("Last Name");
    private TextField username = new TextField("Username");
    private EmailField email = new EmailField("Email");
    private PasswordField password = new PasswordField("Password");
    private PasswordField confirmPassword = new PasswordField("Confirm password");
    private Button registerButton = new Button("Sign Up");

    private User user = new User();

    @Autowired
    public RegisterView(UserService userService) {
        this.userService = userService;

        binder.setBean(user);
        createLayout();
    }

    private void createLayout() {
        Binder.Binding<User, String> firstNameBinding = binder.forField(firstName).bind("firstName");
        firstName.addValueChangeListener(event -> firstNameBinding.validate());
        firstName.setAutoselect(true);
        firstName.setAutofocus(true);

        Binder.Binding<User, String> lastNameBinding = binder.forField(lastName).bind("lastName");
        lastName.addValueChangeListener(event -> lastNameBinding.validate());
        lastName.setAutoselect(true);

        Binder.Binding<User, String> usernameBinding = binder.forField(username).bind("username");
        username.addValueChangeListener(event -> usernameBinding.validate());
        username.setAutoselect(true);

        Binder.Binding<User, String> emailBinding = binder.forField(email).bind("email");
        email.addValueChangeListener(event -> emailBinding.validate());

        Binder.Binding<User, String> passwordBinding = binder.forField(password)
                .withValidator(Validator.from((s -> s.equals(confirmPassword.getValue())),
                        "Passwords don't match")).bind("password");
        password.addValueChangeListener(event -> passwordBinding.validate());
        password.setAutoselect(true);

        Binder.Binding<User, String> confirmPasswordBinding = binder.forField(confirmPassword)
                .withValidator(Validator.from((s -> s.equals(password.getValue())),
                        "Passwords don't match")).bind("password");
        confirmPassword.addValueChangeListener(event -> confirmPasswordBinding.validate());
        confirmPassword.setAutoselect(true);

        registerButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        registerButton.addClickListener(event -> {
            binder.validate();
            Notification.show("zarejestrowano");

            userService.register(user);

        });

        HorizontalLayout names = new HorizontalLayout(firstName, lastName);
        HorizontalLayout usernameAndEmail = new HorizontalLayout(username, email);

        add(names, usernameAndEmail, password, confirmPassword);
        add(registerButton);
    }

}
