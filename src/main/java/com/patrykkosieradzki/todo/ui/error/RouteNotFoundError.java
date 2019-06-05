package com.patrykkosieradzki.todo.ui.error;

import com.patrykkosieradzki.todo.app.HasLogger;
import com.patrykkosieradzki.todo.app.URLNotFoundException;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.ErrorParameter;
import com.vaadin.flow.router.HasErrorParameter;
import com.vaadin.flow.router.NotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;

import javax.servlet.http.HttpServletResponse;

@Tag(Tag.DIV)
public class RouteNotFoundError extends Component
        implements HasErrorParameter<NotFoundException>, HasLogger {

    @Override
    public int setErrorParameter(BeforeEnterEvent event, ErrorParameter<NotFoundException> parameter) {
        getLogger().debug("Path not found: {}", event.getLocation().getPath());
        getElement().setText("Url not found");

        if (event.getLocation().getPath().contains("api")) {
            getLogger().debug("Api request not found");

            throw new URLNotFoundException(HttpMethod.GET.name(), event.getLocation().getPath(),  HttpHeaders.EMPTY);
        }

        return HttpServletResponse.SC_NOT_FOUND;
    }
}