package com.example.rss.controller.feed;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.rss.service.RssReaderService;
import com.example.rss.web.assemblers.CapitalNewsSyndAssembler;
import com.example.rss.web.resources.NewsResource;

import io.swagger.v3.oas.annotations.Operation;

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

  @GetMapping("/capital/bulgaria")
  @Operation(summary = "Get Bulgaria news from Capital", description = "Returns today's Capital Bulgaria news.")
  public List<NewsResource> getCapitalBulgaria() {
    return capitalNewsSyndAssembler
        .toCollectionModel(rssReaderService.readFeed(bulgaria).getEntries())
        .getContent()
        .stream()
        .toList();
  }

  @GetMapping("/capital/world")
  @Operation(summary = "Get world news from Capital", description = "Returns today's Capital World news.")
  public List<NewsResource> getCapitalWorld() {
    return capitalNewsSyndAssembler
        .toCollectionModel(rssReaderService.readFeed(world).getEntries())
        .getContent()
        .stream()
        .toList();
  }

  @GetMapping("/capital/technologies")
  @Operation(summary = "Get technologies news from Capital", description = "Returns today's Capital Technology news.")
  public List<NewsResource> getCapitalTechnologies() {
    return capitalNewsSyndAssembler
        .toCollectionModel(rssReaderService.readFeed(technologies).getEntries())
        .getContent()
        .stream()
        .toList();
  }

  @GetMapping("/capital/galeries")
  public List<NewsResource> getCapitalGaleries() {
    return capitalNewsSyndAssembler
        .toCollectionModel(rssReaderService.readFeed(galeries).getEntries())
        .getContent()
        .stream()
        .toList();
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
