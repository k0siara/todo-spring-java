package com.patrykkosieradzki.todo.backend.util;

import org.springframework.core.env.Environment;

import java.net.InetAddress;
import java.util.Objects;

public class ServerUtils {

    private static Environment environment;

    private ServerUtils() { }

    public static void setEnvironment(Environment environment) {
        ServerUtils.environment = environment;
    }

    public static String getAddress() {
        return "http://" + ServerUtils.getHostName() + ":" + ServerUtils.getPort();
    }

    public static String getHostName() {
        return InetAddress.getLoopbackAddress().getHostName();
    }

    public static int getPort() {
        return Integer.parseInt(Objects.requireNonNull(environment.getProperty("server.port")));
    }
}
