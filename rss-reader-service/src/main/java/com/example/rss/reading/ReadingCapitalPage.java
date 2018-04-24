package com.example.rss.reading;

import java.util.List;

import org.apache.commons.lang.NotImplementedException;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "site.capital")
@Component
public class ReadingCapitalPage extends ReadingPage {

    private String base;
    private String urlSufix;

    @Override
    public String getUrl(int number) {
        return getUrl(String.valueOf(number));
    }

    @Override
    public String getUrl(String identifier) {
        return base + identifier + urlSufix;
    }

    @Override
    public String getUrl() {
        throw new NotImplementedException("This method is not supported.");
    }

    public void setBase(String base) {
        this.base = base;
    }

    public void setUrlSufix(String urlSufix) {
        this.urlSufix = urlSufix;
    }

}
