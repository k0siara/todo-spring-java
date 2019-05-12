package com.patrykkosieradzki.todo.api.config;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.patrykkosieradzki.todo.AppConstants;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static com.google.common.base.Predicates.or;
import static springfox.documentation.builders.PathSelectors.regex;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("api-v1.0")
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.any())
                .apis(Predicates.not(RequestHandlerSelectors.basePackage("org.springframework")))
                .paths(apiPaths())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title(AppConstants.SWAGGER_API_INFO_TITLE)
                .description(AppConstants.SWAGGER_API_INFO_DESCRIPTION)
                .contact(new Contact(AppConstants.DEVELOPER_NAME,
                        AppConstants.DEVELOPER_WEBSITE_URL,
                        AppConstants.DEVELPER_EMAIL))
                .version("1.0")
                .build();
    }

    private Predicate<String> apiPaths() {
        return or(
                regex("/api.*")
        );
    }

}
