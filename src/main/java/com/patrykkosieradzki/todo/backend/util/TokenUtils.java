package com.patrykkosieradzki.todo.backend.util;

import java.util.UUID;

public class TokenUtils {

    private TokenUtils() {}

    public static String getRandomToken() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
}
