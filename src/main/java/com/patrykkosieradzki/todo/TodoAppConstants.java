package com.patrykkosieradzki.todo;

import com.patrykkosieradzki.todo.backend.util.ServerUtils;

public class TodoAppConstants {

    private TodoAppConstants() {}

    public static final String PAGE_ROOT = "";
    public static final String PAGE_DASHBOARD = "dashboard";

    public static final String VIEWPORT = "width=device-width, minimum-scale=1, initial-scale=1, user-scalable=yes";

    public static final String ACTIVATION_ENDPOINT = "/activate?token=";
    public static final String ACTIVATION_EMAIL_SUBJECT = "Welcome to Todo App";

}
