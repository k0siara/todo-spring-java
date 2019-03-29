package com.patrykkosieradzki.todo.ui.component;

import com.vaadin.flow.component.cookieconsent.CookieConsent;

public class MyCookieConsent extends CookieConsent {

    public MyCookieConsent() {
        setPosition(Position.BOTTOM);
        setDismissLabel("Cool!");
    }
}
