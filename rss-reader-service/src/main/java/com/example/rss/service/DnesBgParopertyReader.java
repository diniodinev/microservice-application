package com.example.rss.service;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 
 * Maps read values from cloud-config server to a <code>Map</code>.
 */
@Component
public class DnesBgParopertyReader {

    @Value("${site.dnesbg.rootUrl}")
    private String rootUrl;

    @Value("${site.dnesbg.last}")
    private String last;

    @Value("${site.dnesbg.titleSelector}")
    private String title;

    @Value("${site.dnesbg.descriptionSelector}")
    private String description;

    @Value("${site.dnesbg.contentSelector}")
    private String content;

    public Map<String, String> getProperties() {
        final Map<String, String> properties = new HashMap<>();
        properties.put(DnesBgParamEnum.ROOTURL.name(), rootUrl);
        properties.put(DnesBgParamEnum.LAST.name(), last);
        properties.put(DnesBgParamEnum.TITLE.name(), title);
        properties.put(DnesBgParamEnum.DESCRIPTION.name(), description);
        properties.put(DnesBgParamEnum.CONTENT.name(), content);

        return Collections.unmodifiableMap(properties);
    }
}
