package com.patrykkosieradzki.todo.ui;

import com.patrykkosieradzki.todo.TodoAppConstants;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.page.Viewport;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;


@Route
@PageTitle("Register")
@Viewport(TodoAppConstants.VIEWPORT)
public class RegisterView extends VerticalLayout {

    public RegisterView() {

        TextField firstNameField = new TextField();

        add(firstNameField);

    }
}
