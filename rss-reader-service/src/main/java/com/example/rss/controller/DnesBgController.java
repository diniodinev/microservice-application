package com.example.rss.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.rss.service.RssReaderService;
import com.example.rss.web.assemblers.DnesbgNewsResourceAssemblers;
import com.example.rss.web.resources.NewsResource;

import io.swagger.annotations.ApiOperation;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RefreshScope
@RequestMapping(value = "/dnesbg")
public class DnesBgController extends AbstractController {

    @Value("${configuration.projectName}")
    private String projectName;

    @Value("${site.dnesbg.today}")
    private String dnesbgToday;

    @Value("${site.dnesbg.world}")
    private String dnesbgWorld;

    @Autowired
    private DnesbgNewsResourceAssemblers newsResourceAssemblers;

    @Autowired
    private RssReaderService rssReaderService;

    @RequestMapping("/project-name")

    @ApiIgnore
    public String projectName() {
        return this.projectName;
    }

    @ApiOperation(
            value = "Get todays'news as Rss.",
            notes = "This is only basic information for the today's news.",
            response = NewsResource.class)
    @RequestMapping(value = "/today", method = RequestMethod.GET)
    public NewsResource getDnesBgToday() {
        return newsResourceAssemblers.toResource(rssReaderService.readFeed(dnesbgToday).getEntries().get(0));
    }

    @ApiOperation(value = "Get All todays'news as Rss.", notes = "This is only basic information for all today's news.")
    @RequestMapping(value = "/today/all", method = RequestMethod.GET)
    public List<NewsResource> getAllNewsToday() {
        return newsResourceAssemblers.toResources(rssReaderService.readFeed(dnesbgToday).getEntries());
    }

    @ApiOperation(
            value = "Get last todays news for the World Category as RSS.",
            notes = "This is only basic information for the last today's news from World category.")
    @RequestMapping(value = "/world", method = RequestMethod.GET)
    public NewsResource getDnesBgWorld() {
        return newsResourceAssemblers.toResource(rssReaderService.readFeed(dnesbgWorld).getEntries().get(0));
    }

    @ApiOperation(
            value = "Get All todays'news for the World Category as RSS.",
            notes = "This is only basic information for all today's news from World category.")
    @RequestMapping(value = "/world/all", method = RequestMethod.GET)
    public List<NewsResource> getAllNewsWorld() {
        return newsResourceAssemblers.toResources(rssReaderService.readFeed(dnesbgWorld).getEntries());
    }

}
