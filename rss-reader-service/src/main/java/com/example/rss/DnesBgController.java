package com.example.rss;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.rss.resources.NewsResource;
import com.example.rss.resources.NewsResourceAssemblers;
import com.example.rss.service.RssReaderService;
import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.io.FeedException;

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
    NewsResourceAssemblers newsResourceAssemblers;

    @Autowired
    RssReaderService rssReaderService;

    @RequestMapping(value = "/dnesbg/today", method = RequestMethod.GET)
    public NewsResource getDnesBgToday() throws IllegalArgumentException, FeedException, IOException {
        return newsResourceAssemblers.toResource((SyndEntry) rssReaderService.redFeed(dnesbgToday).getEntries().get(0));
    }

    @RequestMapping(value = "/dnesbg/today/all", method = RequestMethod.GET)
    public List<NewsResource> getAllNewsToday() throws IllegalArgumentException, FeedException, IOException {
        return newsResourceAssemblers.toResources(rssReaderService.redFeed(dnesbgToday).getEntries());
    }

    @RequestMapping("/dnesbg/world")
    public List<SyndEntry> getDnesBgWorld() throws IllegalArgumentException, FeedException, IOException {
        return rssReaderService.redFeed(dnesbgToday).getEntries();
    }

}
