package com.example.rss.reading;

import org.apache.commons.lang.NotImplementedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import com.example.rss.config.DnesbgProperties;
import com.example.rss.utils.DnesBgParamEnum;

@ConfigurationProperties(prefix = "site.dnesbg")
@Component
public class ReadingDnesBgPage extends ReadingPage {
    @Autowired
    private DnesbgProperties serviceProperties;

    @Override
    public String getUrl(int number) {
        return serviceProperties.getDnesbg().get(DnesBgParamEnum.rootUrl.name()) + number;
    }

    @Override
    public String getUrl(String identifier) {
        return serviceProperties.getDnesbg().get(DnesBgParamEnum.rootUrl.name()) + identifier;
    }

    @Override
    public String getUrl() {
        throw new NotImplementedException("This method is not supported.");
    }

}
