package com.example.rss.controller.feed;

import com.example.rss.service.RssReaderService;
import com.example.rss.web.assemblers.BbcNewsResourceAssemblers;
import com.example.rss.web.resources.NewsResource;
import com.rometools.rome.feed.synd.SyndEntry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RefreshScope
public class BbcFeedController {

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
    private RssReaderService rssReaderService;

    @GetMapping("/bbc/topstories")
    public NewsResource getBbcTopStories() {
        return bbcNewsResourceAssemblers.toModel(rssReaderService.readFeed(bbcTopstoris).getEntries().get(0));
    }

    @GetMapping("/bbc/topstories/all")
    public CollectionModel<NewsResource> getAllBbcTopStories() {
        return bbcNewsResourceAssemblers.toCollectionModel(rssReaderService.readFeed(bbcTopstoris).getEntries());
    }

    @GetMapping("/bbc/world")
    public NewsResource getBbcWorld() {
        return bbcNewsResourceAssemblers.toModel(rssReaderService.readFeed(bbcWorld).getEntries().get(0));
    }

    @GetMapping("/bbc/world/all")
    public CollectionModel<NewsResource> getAllBbcWorld() {
        return bbcNewsResourceAssemblers.toCollectionModel(rssReaderService.readFeed(bbcWorld).getEntries());
    }

    @GetMapping("/bbc/uk")
    public NewsResource getBbcUk() {
        return bbcNewsResourceAssemblers.toModel(rssReaderService.readFeed(bbcUk).getEntries().get(0));
    }

    @GetMapping("/bbc/uk/all")
    public CollectionModel<NewsResource> getAllBbcUk() {
        return bbcNewsResourceAssemblers.toCollectionModel(rssReaderService.readFeed(bbcUk).getEntries());
    }

    @GetMapping("/bbc/science")
    public NewsResource getBbcScience() {
        return bbcNewsResourceAssemblers.toModel(rssReaderService.readFeed(bbcScience).getEntries().get(0));
    }

    @GetMapping("/bbc/science/all")
    public CollectionModel<NewsResource> getAllBbcScience() {
        return bbcNewsResourceAssemblers.toCollectionModel(rssReaderService.readFeed(bbcScience).getEntries());
    }
}
