package com.patrykkosieradzki.todo.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiInfo {

    private String version;
    private String documentationUrl;
}
