package com.patrykkosieradzki.todo.api.controller;

import com.patrykkosieradzki.todo.AppConstants;
import com.patrykkosieradzki.todo.api.entity.ApiInfo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api")
public class ApiController {

    @Value("${server.port}")
    int port;

    @GetMapping
    public ApiInfo home(HttpServletRequest request) {
        return new ApiInfo("1.0", request.getServerName() + ":" +  port + AppConstants.SWAGGER_ENDPOINT);
    }



}
