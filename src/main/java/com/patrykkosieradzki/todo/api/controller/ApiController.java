package com.patrykkosieradzki.todo.api.controller;

import com.patrykkosieradzki.todo.AppConstants;
import com.patrykkosieradzki.todo.api.ApiInfo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ApiController {

    @GetMapping
    public ApiInfo home() {
        return new ApiInfo("1.0", AppConstants.SWAGGER_URL);
    }

}
