package com.patrykkosieradzki.todo.ui;

import com.patrykkosieradzki.todo.AppConstants;
import com.patrykkosieradzki.todo.app.security.SecurityUtils;
import com.vaadin.flow.component.HasElement;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AbstractAppRouterLayout;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.AppLayoutMenu;
import com.vaadin.flow.component.applayout.AppLayoutMenuItem;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.page.Viewport;

@HtmlImport("styles/shared-styles.html")
@Viewport(AppConstants.VIEWPORT)
public class MainView extends AbstractAppRouterLayout {

    @Override
    protected void configure(AppLayout appLayout, AppLayoutMenu menu) {
        Image image = new Image("https://todogroup.org/img/logo.svg", "Logo");
        image.setHeight("50px");

        appLayout.setBranding(image);

        addMenuItem(menu, new AppLayoutMenuItem("REST API", e ->
                executeJavaScript("window.open(\"" + AppConstants.SWAGGER_URL + "\", \"_self\");")));

        if (SecurityUtils.isUserLoggedIn()) {
            addMenuItem(menu, new AppLayoutMenuItem("Logout", e ->
                    executeJavaScript("location.assign('logout')")));
        } else {
            addMenuItem(menu, new AppLayoutMenuItem("Login", e ->
                    executeJavaScript("location.assign('login')")));
        }
    }

    private void addMenuItem(AppLayoutMenu menu, AppLayoutMenuItem menuItem) {
        menuItem.getElement().setAttribute("theme", "icon-on-top");
        menu.addMenuItem(menuItem);
    }

    @Override
    public void showRouterLayoutContent(HasElement content) {
        super.showRouterLayoutContent(content);
    }

    private void executeJavaScript(String js) {
        UI.getCurrent().getPage().executeJavaScript(js);
    }
}
