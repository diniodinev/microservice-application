package com.example.rss.utils.tags;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "site.dnesbg")
@Component
public class DnesbgContentTags extends ContentTags {

}
