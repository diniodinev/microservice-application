package com.example.rss.reading;

import javax.annotation.Resource;

import org.apache.commons.lang.NotImplementedException;
import org.springframework.stereotype.Component;

import com.example.rss.config.DnesbgProperties;
import com.example.rss.utils.DnesBgParamEnum;

@Component
public class ReadingDnesBgPage extends ReadingPage {
    @Resource
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
