package com.patrykkosieradzki.todo.ui.utils;

import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.Map;

@Component
public class ThymeleafUtils {

    private static TemplateEngine templateEngine;

    public static void setTemplateEngine(TemplateEngine templateEngine) {
        ThymeleafUtils.templateEngine = templateEngine;
    }

    public static String getProcessedHtml(Map<String, Object> variables, String templateName) {
        Context context = new Context(null, variables);
        return templateEngine.process(templateName, context);
    }
}
