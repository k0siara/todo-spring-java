package com.patrykkosieradzki.todo.app;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class ApplicationContextProvider implements ApplicationContextAware {

    private static ApplicationContext context;

    @Override
    public void setApplicationContext(ApplicationContext context) throws BeansException {
        ApplicationContextProvider.context = context;
    }

    public static Object getBean(Class clazz) {
        return ApplicationContextProvider.context.getBean(clazz);
    }
}
