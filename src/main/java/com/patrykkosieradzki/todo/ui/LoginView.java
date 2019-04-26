package com.patrykkosieradzki.todo.ui;

import com.patrykkosieradzki.todo.AppConstants;
import com.patrykkosieradzki.todo.app.security.SecurityUtils;
import com.patrykkosieradzki.todo.ui.component.CustomCookieConsent;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.login.LoginI18n;
import com.vaadin.flow.component.login.LoginOverlay;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.page.Viewport;
import com.vaadin.flow.router.*;

@Route
@PageTitle("Todo App")
@Viewport(AppConstants.VIEWPORT)
public class LoginView extends VerticalLayout implements AfterNavigationObserver, BeforeEnterObserver {

    private LoginOverlay login;

    public LoginView() {
        add(new CustomCookieConsent());

        LoginI18n i18n = LoginI18n.createDefault();
        i18n.setHeader(new LoginI18n.Header());
        i18n.getHeader().setTitle("Todo Application");
        i18n.setAdditionalInformation(null);

        login = new LoginOverlay();
        login.setI18n(i18n);
        login.setForgotPasswordButtonVisible(false);
        login.setAction("login");
        login.setOpened(true);

        add(login);
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        if (SecurityUtils.isUserLoggedIn()) {
            // Needed manually to change the URL because of https://github.com/vaadin/flow/issues/4189
            UI.getCurrent().getPage().getHistory().replaceState(null, "");
            event.rerouteTo(DashboardView.class);
            login.setOpened(false);
        }
    }

    @Override
    public void afterNavigation(AfterNavigationEvent event) {
        login.setError(event.getLocation().getQueryParameters().getParameters().containsKey("error"));
    }
}
