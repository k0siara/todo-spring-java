package com.patrykkosieradzki.todo;

import com.patrykkosieradzki.todo.backend.util.ServerUtils;

public class AppConstants {

    public static final String DEVELOPER_NAME = "Patryk Kosieradzki";
    public static final String DEVELOPER_WEBSITE_URL = "http://patrykkosieradzki.com";
    public static final String DEVELPER_EMAIL = "patryk.kosieradzki@gmail.com";

    public static final String SWAGGER_API_INFO_TITLE = "Todo API";
    public static final String SWAGGER_API_INFO_DESCRIPTION = "REST API for Todo App";

    private AppConstants() {}

    public static final String PAGE_ROOT = "";
    public static final String PAGE_DASHBOARD = "dashboard";

    public static final String ROOT_URL = "/";
    public static final String LOGIN_URL = "/login";
    public static final String LOGIN_FAILURE_URL = "/login?error";

    public static final String VIEWPORT = "width=device-width, minimum-scale=1, initial-scale=1, user-scalable=yes";

    public static final String ACTIVATION_ADDRESS = ServerUtils.getAddress() + "/activate?token=";
    public static final String ACTIVATION_EMAIL_SUBJECT = "Welcome to Todo App";

    public static final String SWAGGER_ENDPOINT = "/swagger-ui.html";
    public static final String SWAGGER_URL = ServerUtils.getAddress() + SWAGGER_ENDPOINT;

    public static final String FIND_ACTIVATION_TOKEN_BY_ID_PATH =
            "com.patrykkosieradzki.todo.backend.repository.ActivationTokenRepository.findById";

    public static final String FIND_TODOS_BY_USER_USERNAME =
            "com.patrykkosieradzki.todo.backend.repository.TodoRepository.findAllByUserUsername";

    public static final String FIND_USER_BY_ID_PATH =
            "com.patrykkosieradzki.todo.backend.repository.UserRepository.findById";

    public static final String FIND_ROLES_BY_USER_ID =
            "com.patrykkosieradzki.todo.backend.repository.RoleRepository.findAllByUserId";

}
