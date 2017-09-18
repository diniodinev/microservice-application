package com.example.rss.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.rss.resources.NewsResource;
import com.example.rss.resources.DnesbgNewsResourceAssemblers;
import com.example.rss.service.RssReaderService;
import com.rometools.rome.feed.synd.SyndEntry;

@RestController
@RefreshScope
public class DnesBgController {

    @Value("${configuration.projectName}")
    private String projectName;

    @Value("${site.dnesbg.today}")
    private String dnesbgToday;

    @Value("${site.dnesbg.world}")
    private String dnesbgWorld;

    @RequestMapping("/project-name")
    public String projectName() {
        return this.projectName;
    }

    @Autowired
    DnesbgNewsResourceAssemblers newsResourceAssemblers;

    @Autowired
    RssReaderService rssReaderService;

    @RequestMapping(value = "/dnesbg/today", method = RequestMethod.GET)
    public NewsResource getDnesBgToday() {
        return newsResourceAssemblers
                .toResource((SyndEntry) rssReaderService.readFeed(dnesbgToday).getEntries().get(0));
    }

    @RequestMapping(value = "/dnesbg/today/all", method = RequestMethod.GET)
    public List<NewsResource> getAllNewsToday() {
        return newsResourceAssemblers.toResources(rssReaderService.readFeed(dnesbgToday).getEntries());
    }

    @RequestMapping("/dnesbg/world")
    public NewsResource getDnesBgWorld() {
        return newsResourceAssemblers
                .toResource((SyndEntry) rssReaderService.readFeed(dnesbgWorld).getEntries().get(0));
    }

    @RequestMapping("/dnesbg/world/all")
    public List<NewsResource> getAllNewsWorld() {
        return newsResourceAssemblers.toResources(rssReaderService.readFeed(dnesbgWorld).getEntries());
    }

}
