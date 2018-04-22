package com.example.rss.controller.feed;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.rss.service.RssReaderService;
import com.example.rss.web.assemblers.CapitalNewsSyndAssembler;
import com.example.rss.web.resources.NewsResource;

@RestController
@RefreshScope
@ConfigurationProperties(prefix = "site.capital")
public class CapitalFeedController extends AbstractController {

    private String bulgaria;
    private String world;
    private String technologies;
    private String galeries;

    @Autowired
    RssReaderService rssReaderService;

    @Autowired
    CapitalNewsSyndAssembler capitalNewsSyndAssembler;

    @RequestMapping(value = "/capital/bulgaria", method = RequestMethod.GET)
    public List<NewsResource> getCapitalBulgaria() {
        return capitalNewsSyndAssembler
                .toResources(rssReaderService.readFeed(bulgaria).getEntries());
    }

    @RequestMapping(value = "/capital/world", method = RequestMethod.GET)
    public List<NewsResource> getCapitalWorld() {
        return capitalNewsSyndAssembler.toResources(rssReaderService.readFeed(world).getEntries());
    }

    @RequestMapping(value = "/capital/technologies", method = RequestMethod.GET)
    public List<NewsResource> getCapitalTechnologies() {
        return capitalNewsSyndAssembler.toResources(rssReaderService.readFeed(technologies).getEntries());
    }

    @RequestMapping(value = "/capital/galeries", method = RequestMethod.GET)
    public List<NewsResource> getCapitalGaleries() {
        return capitalNewsSyndAssembler.toResources(rssReaderService.readFeed(galeries).getEntries());
    }

    public void setBulgaria(String bulgaria) {
        this.bulgaria = bulgaria;
    }

    public void setWorld(String world) {
        this.world = world;
    }

    public void setTechnologies(String technologies) {
        this.technologies = technologies;
    }

    public void setGaleries(String galeries) {
        this.galeries = galeries;
    }
}
