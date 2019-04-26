package com.patrykkosieradzki.todo.app;

import com.patrykkosieradzki.todo.backend.util.ServerUtils;
import com.patrykkosieradzki.todo.ui.utils.ThymeleafUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;

import javax.annotation.PostConstruct;

@Component
public class ApplicationContextProvider implements ApplicationContextAware {

    private static ApplicationContext context;

    private Environment environment;
    private TemplateEngine templateEngine;

    @Autowired
    public ApplicationContextProvider(Environment environment, TemplateEngine templateEngine) {
        this.environment = environment;
        this.templateEngine = templateEngine;
    }

    @Override
    public void setApplicationContext(ApplicationContext context) throws BeansException {
        ApplicationContextProvider.context = context;
    }

    public static Object getBean(Class clazz) {
        return ApplicationContextProvider.context.getBean(clazz);
    }

    @PostConstruct
    public void initializeStaticContext() {
        ServerUtils.setEnvironment(environment);
        ThymeleafUtils.setTemplateEngine(templateEngine);
    }

}
