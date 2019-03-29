package com.patrykkosieradzki.todo.ui;

import com.patrykkosieradzki.todo.ui.component.MyCookieConsent;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.login.LoginI18n;
import com.vaadin.flow.component.login.LoginOverlay;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import com.vaadin.flow.templatemodel.TemplateModel;

@Route
@HtmlImport("styles/shared-styles.html")
public class LoginView extends VerticalLayout implements AfterNavigationObserver, BeforeEnterObserver {

    private LoginOverlay login = new LoginOverlay();

    public LoginView() {
        getElement().appendChild(
                new MyCookieConsent().getElement(), login.getElement());

        LoginI18n i18n = LoginI18n.createDefault();
        i18n.setHeader(new LoginI18n.Header());
        i18n.getHeader().setTitle("dsa");
        i18n.getHeader().setDescription(
                "admin@vaadin.com + admin\n" + "barista@vaadin.com + barista");
        i18n.setAdditionalInformation(null);
        i18n.setForm(new LoginI18n.Form());
        i18n.getForm().setSubmit("Sign in");
        i18n.getForm().setTitle("Sign in");
        i18n.getForm().setUsername("Email");
        i18n.getForm().setPassword("Password");
        login.setI18n(i18n);
        login.getElement().setAttribute("no-forgot-password", true);
        login.setAction("login");
        login.setOpened(true);
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {

    }

    @Override
    public void afterNavigation(AfterNavigationEvent event) {
        login.setError(
                event.getLocation().getQueryParameters().getParameters().containsKey(
                        "error"));
    }

    public interface Model extends TemplateModel {

        void setError(boolean error);
    }
}
