package com.patrykkosieradzki.todo;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import com.vaadin.flow.server.VaadinServletConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

@VaadinServletConfiguration(productionMode = true)
@EnableEncryptableProperties
@SpringBootApplication
public class TodoSpringJavaApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        ConfigurableApplicationContext ctx = SpringApplication.run(TodoSpringJavaApplication.class, args);

        DispatcherServlet dispatcherServlet = (DispatcherServlet) ctx.getBean("dispatcherServlet");
        dispatcherServlet.setThrowExceptionIfNoHandlerFound(true);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(TodoSpringJavaApplication.class);
    }
}
