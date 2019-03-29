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

        LoginOverlay component = new LoginOverlay();

        LoginI18n i18n = LoginI18n.createDefault();
        i18n.setHeader(new LoginI18n.Header());
        i18n.getHeader().setTitle("Todo Application");
        i18n.setAdditionalInformation(null);

        component.setI18n(i18n);
        component.setForgotPasswordButtonVisible(false);
        component.setAction("login");
        component.setOpened(true);
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
