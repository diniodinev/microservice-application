package com.example.rss.reading;

import org.apache.commons.lang.NotImplementedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.rss.config.DnesbgProperties;
import com.example.rss.service.DnesBgParamEnum;

@Component
public class ReadingDnesBgPage extends ReadingPage {
    @Autowired
    private DnesbgProperties serviceProperties;

    @Override
    public String getUrl(int number) {
        return serviceProperties.getDnesbg().get(DnesBgParamEnum.rootUrl.name()) + number;
    }

    @Override
    public String getUrl() {
        throw new NotImplementedException("This method is not supported.");
    }
}
