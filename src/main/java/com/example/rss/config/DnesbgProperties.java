package com.example.rss.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties("site")
@Component
public class DnesbgProperties {

    private static final Map<String, String> dnesbg = new HashMap<>();

    public Map<String, String> getDnesbg() {
        return dnesbg;
    }

}
