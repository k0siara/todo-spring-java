package com.patrykkosieradzki.todo.api;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ApiInfo {

    private String version;
    private String documentationUrl;
}
