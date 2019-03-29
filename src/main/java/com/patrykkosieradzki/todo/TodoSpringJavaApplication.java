package com.patrykkosieradzki.todo;

import com.vaadin.flow.server.VaadinServletConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@VaadinServletConfiguration(productionMode = true)
@SpringBootApplication
public class TodoSpringJavaApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(TodoSpringJavaApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(TodoSpringJavaApplication.class);
    }
}
