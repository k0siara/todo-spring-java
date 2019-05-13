package com.patrykkosieradzki.todo.api.controller;

import com.patrykkosieradzki.todo.AppConstants;
import com.patrykkosieradzki.todo.api.ApiInfo;
import com.patrykkosieradzki.todo.backend.util.ServerUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.net.MalformedURLException;

@RestController
@RequestMapping("/api")
public class ApiController {

    @GetMapping
    public ApiInfo home(HttpServletRequest request) throws MalformedURLException {
        return new ApiInfo("1.0", ServerUtils.getURLBase(request) + AppConstants.SWAGGER_ENDPOINT);
    }



}
