package com.example.rss.utils.tags;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "site.capital")
@Component
public class CapitalNewsTags extends NewsTags {

    private String secondTitle;
    private String initialDateAttribute;

    public String getInitialDateAttribute() {
        return initialDateAttribute;
    }

    public void setInitialDateAttribute(String initialDateAttribute) {
        this.initialDateAttribute = initialDateAttribute;
    }

    public String getSecondTitle() {
        return secondTitle;
    }

    public void setSecondTitle(String secondTitle) {
        this.secondTitle = secondTitle;
    }

}
