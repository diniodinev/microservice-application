package com.example.rss.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.rss.config.DnesbgProperties;
import com.example.rss.service.DnesBgParamEnum;

@Component
public class DnesBgParams {

    @Autowired
    private DnesbgProperties serviceProperties;

    public String getDescription() {
        return serviceProperties.getDnesbg().get(DnesBgParamEnum.descriptionSelector.name());
    }

    public String getContent() {
        return serviceProperties.getDnesbg().get(DnesBgParamEnum.contentSelector.name());
    }

    public String getAuthor() {
        return serviceProperties.getDnesbg().get(DnesBgParamEnum.authorSelector.name());
    }

    public String getTitle() {
        return serviceProperties.getDnesbg().get(DnesBgParamEnum.titleSelector.name());
    }

    public String getCreatedDate() {
        return serviceProperties.getDnesbg().get(DnesBgParamEnum.createdDateSelector.name());
    }

}
