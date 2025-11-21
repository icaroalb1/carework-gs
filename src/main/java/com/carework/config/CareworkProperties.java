package com.carework.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.UUID;

@ConfigurationProperties(prefix = "carework")
public class CareworkProperties {

    private final Security security = new Security();
    private final Web web = new Web();
    private final Demo demo = new Demo();

    public Security getSecurity() {
        return security;
    }

    public Web getWeb() {
        return web;
    }

    public Demo getDemo() {
        return demo;
    }

    public static class Security {
        private String apiKey;

        public String getApiKey() {
            return apiKey;
        }

        public void setApiKey(String apiKey) {
            this.apiKey = apiKey;
        }
    }

    public static class Web {
        private String apiBaseUrl;

        public String getApiBaseUrl() {
            return apiBaseUrl;
        }

        public void setApiBaseUrl(String apiBaseUrl) {
            this.apiBaseUrl = apiBaseUrl;
        }
    }

    public static class Demo {
        private UUID userId;

        public UUID getUserId() {
            return userId;
        }

        public void setUserId(UUID userId) {
            this.userId = userId;
        }
    }
}
