package com.example.rss.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.rss.resources.BbcNewsResourceAssemblers;
import com.example.rss.resources.NewsResource;
import com.example.rss.service.RssReaderService;

@RestController
@RefreshScope
@RequestMapping(value = "/bbc")
public class BbcController extends AbstractController {

    @Value("${site.bbc.topstories}")
    private String bbcTopstoris;

    @Value("${site.bbc.world}")
    private String bbcWorld;

    @Value("${site.bbc.uk}")
    private String bbcUk;

    @Value("${site.bbc.science}")
    private String bbcScience;

    @Autowired
    private BbcNewsResourceAssemblers bbcNewsResourceAssemblers;

    @Autowired
    RssReaderService rssReaderService;

    @RequestMapping(value = "/topstories", method = RequestMethod.GET)
    public NewsResource getBbcTopStories() {
        return bbcNewsResourceAssemblers
                .toResource(rssReaderService.readFeed(bbcTopstoris).getEntries().get(0));
    }

    @RequestMapping(value = "/topstories/all", method = RequestMethod.GET)
    public List<NewsResource> getAllBbcTopStories() {
        return bbcNewsResourceAssemblers.toResources(rssReaderService.readFeed(bbcTopstoris).getEntries());
    }

    @RequestMapping(value = "/world", method = RequestMethod.GET)
    public NewsResource getBbcWorld() {
        return bbcNewsResourceAssemblers
                .toResource(rssReaderService.readFeed(bbcWorld).getEntries().get(0));
    }

    @RequestMapping(value = "/world/all", method = RequestMethod.GET)
    public List<NewsResource> getAllBbcWorld() {
        return bbcNewsResourceAssemblers.toResources(rssReaderService.readFeed(bbcWorld).getEntries());
    }

    @RequestMapping(value = "/bbcUk", method = RequestMethod.GET)
    public NewsResource getBbcUk() {
        return bbcNewsResourceAssemblers.toResource(rssReaderService.readFeed(bbcUk).getEntries().get(0));
    }

    @RequestMapping(value = "/bbcUk/all", method = RequestMethod.GET)
    public List<NewsResource> getAllBbcUk() {
        return bbcNewsResourceAssemblers.toResources(rssReaderService.readFeed(bbcUk).getEntries());
    }

    @RequestMapping(value = "/science", method = RequestMethod.GET)
    public NewsResource getBbcScience() {
        return bbcNewsResourceAssemblers
                .toResource(rssReaderService.readFeed(bbcScience).getEntries().get(0));
    }

    @RequestMapping(value = "/science/all", method = RequestMethod.GET)
    public List<NewsResource> getAllBbcScience() {
        return bbcNewsResourceAssemblers.toResources(rssReaderService.readFeed(bbcScience).getEntries());
    }
}
