package com.patrykkosieradzki.todo.ui;

import com.patrykkosieradzki.todo.ui.component.MyCookieConsent;
import com.vaadin.flow.component.HasElement;
import com.vaadin.flow.component.applayout.AbstractAppRouterLayout;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.AppLayoutMenu;
import com.vaadin.flow.component.applayout.AppLayoutMenuItem;
import com.vaadin.flow.component.confirmdialog.ConfirmDialog;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.page.Viewport;

@HtmlImport("styles/shared-styles.html")
@Viewport("width=device-width, minimum-scale=1, initial-scale=1, user-scalable=yes")
public class MainView extends AbstractAppRouterLayout {

    private final ConfirmDialog confirmDialog;

    public MainView() {
        this.confirmDialog = new ConfirmDialog();
        confirmDialog.setCancelable(true);
        confirmDialog.setConfirmButtonTheme("raised tertiary error");
        confirmDialog.setCancelButtonTheme("raised tertiary");

        getElement().appendChild(confirmDialog.getElement());
        getElement().appendChild(new MyCookieConsent().getElement());
    }

    @Override
    protected void configure(AppLayout appLayout, AppLayoutMenu menu) {
        appLayout.setBranding(new Span("Todo Application"));


        menu.addMenuItem("DUPA");

//        setMenuItem(menu, new AppLayoutMenuItem(VaadinIcon.EDIT.create(), "tytul create"));

//        if (SecurityUtils.isUserLoggedIn()) {
//            setMenuItem(menu, new AppLayoutMenuItem(VaadinIcon.EDIT.create(), TITLE_STOREFRONT, PAGE_STOREFRONT));
//            setMenuItem(menu, new AppLayoutMenuItem(VaadinIcon.CLOCK.create(), TITLE_DASHBOARD, PAGE_DASHBOARD));
//
//            if (SecurityUtils.isAccessGranted(UsersView.class)) {
//                setMenuItem(menu, new AppLayoutMenuItem(VaadinIcon.USER.create(), TITLE_USERS, PAGE_USERS));
//            }
//            if (SecurityUtils.isAccessGranted(ProductsView.class)) {
//                setMenuItem(menu, new AppLayoutMenuItem(VaadinIcon.CALENDAR.create(), TITLE_PRODUCTS, PAGE_PRODUCTS));
//            }
//
//            setMenuItem(menu, new AppLayoutMenuItem(VaadinIcon.ARROW_RIGHT.create(), "Wyloguj", e ->
//                    UI.getCurrent().getPage().executeJavaScript("location.assign('logout')")));
//        }

        getElement().addEventListener("search-focus", e -> {
            appLayout.getElement().getClassList().add("hide-navbar");
        });

        getElement().addEventListener("search-blur", e -> {
            appLayout.getElement().getClassList().remove("hide-navbar");
        });
    }

    private void setMenuItem(AppLayoutMenu menu, AppLayoutMenuItem menuItem) {
        menuItem.getElement().setAttribute("theme", "icon-on-top");
        menu.addMenuItem(menuItem);
    }

    @Override
    public void showRouterLayoutContent(HasElement content) {
        super.showRouterLayoutContent(content);

//        this.confirmDialog.setOpened(false);
//        if (content instanceof HasConfirmation) {
//            ((HasConfirmation) content).setConfirmDialog(this.confirmDialog);
//        }
    }
}
