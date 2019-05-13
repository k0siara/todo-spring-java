package com.patrykkosieradzki.todo.backend.util;

import org.springframework.core.env.Environment;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Objects;

public class ServerUtils {

    private static Environment environment;

    private ServerUtils() { }

    public static void setEnvironment(Environment environment) {
        ServerUtils.environment = environment;
    }

    public static String getAddress() {
        return "http://" + getHostName() + ":" + ServerUtils.getPort();
    }

    public static String getHostName() {
        return InetAddress.getLoopbackAddress().getHostName();
    }

    public static int getPort() {
        return Integer.parseInt(Objects.requireNonNull(environment.getProperty("server.port")));
    }

    public static String getURLBase(HttpServletRequest request) throws MalformedURLException {
        URL requestURL = new URL(request.getRequestURL().toString());
        String port = requestURL.getPort() == -1 ? "" : ":" + requestURL.getPort();
        return requestURL.getProtocol() + "://" + requestURL.getHost() + port;
    }
}
