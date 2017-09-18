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
import com.rometools.rome.feed.synd.SyndEntry;

@RestController
@RefreshScope
@RequestMapping(value = "/bbc")
public class BbcController {

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
    public NewsResource getBbcToday() {
        return bbcNewsResourceAssemblers
                .toResource((SyndEntry) rssReaderService.readFeed(bbcTopstoris).getEntries().get(0));
    }

    @RequestMapping(value = "/topstories/all", method = RequestMethod.GET)
    public List<NewsResource> getAllNewsToday() {
        return bbcNewsResourceAssemblers.toResources(rssReaderService.readFeed(bbcTopstoris).getEntries());
    }

}
