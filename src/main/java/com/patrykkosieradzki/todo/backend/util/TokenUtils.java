package com.patrykkosieradzki.todo.backend.util;

import java.util.concurrent.ThreadLocalRandom;

public class TokenUtils {

    private final static String characters = "1234567890qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM";

    private TokenUtils() {}

    public static String getRandomToken(int length) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < length; i++) {
            sb.append(characters.charAt(ThreadLocalRandom.current().nextInt(0, characters.length())));
        }

        return sb.toString();
    }
}
