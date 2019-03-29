package com.patrykkosieradzki.todo.ui;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import org.springframework.beans.factory.annotation.Autowired;

@HtmlImport("styles/shared-styles.html")
@Route(value = "dashboard", layout = MainView.class)
@RouteAlias(value = "", layout = MainView.class)
@PageTitle("Dashboard Todo app")
public class DashboardView extends VerticalLayout {


    private TodoLayout todoLayout;

    @Autowired
    public DashboardView(TodoLayout todoLayout) {
        this.todoLayout = todoLayout;


        setDefaultHorizontalComponentAlignment(Alignment.CENTER);

        Label header = new Label("TODO");
        add(header);

        HorizontalLayout formLayout = new HorizontalLayout();
        formLayout.setWidth("60%");

        TextField taskField = new TextField();
        Button addButton = new Button("Add");

        formLayout.add(taskField, addButton);
        formLayout.expand(taskField);

        add(formLayout);


        todoLayout.setWidth("80%");
        add(todoLayout);

        Button deleteButton = new Button("Delete completed");
        add(deleteButton);

    }


}
