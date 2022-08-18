package com.example.urlshortener.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.net.InetAddress;
import java.net.UnknownHostException;

@Component
public class EnvUtil {

    private final Environment environment;

    @Value("${server.port}")
    private String port;

    private String hostname;

    @Value("${server.servlet.context-path}")
    private String serverServletContextPath;

    public EnvUtil(Environment environment) {
        this.environment = environment;
    }

    public String getPort() {
        String p = environment.getProperty("local.server.port");
        if(p != null) port = p;
        return port;
    }

    public Integer getPortAsInt() {
        return Integer.valueOf(getPort());
    }

    public String getHostname() throws UnknownHostException {
        if (hostname == null) hostname = InetAddress.getLocalHost().getHostAddress();
        return hostname;
    }

    /**
     * If we have any domain name we can put here
     */
    public String getBaseUrl() throws UnknownHostException {
        return "http://" + getHostname() + ":" + getPort() + serverServletContextPath;
    }
}