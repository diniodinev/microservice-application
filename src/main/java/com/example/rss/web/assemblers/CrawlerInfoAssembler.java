package com.example.rss.web.assemblers;

import java.util.LinkedList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.example.rss.entity.CrawlerInfo;
import com.example.rss.web.resources.CrawlerInfoResource;

import jakarta.annotation.Nullable;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Component
public class CrawlerInfoAssembler implements RepresentationModelAssembler<CrawlerInfo, CrawlerInfoResource> {

  @Override
  @Nullable
  public CrawlerInfoResource toModel(CrawlerInfo entity) {
    if (entity == null) {
      return null;
    }

    CrawlerInfoResource contentResource = new CrawlerInfoResource();
    contentResource.setCrawledSiteName(entity.getCrawledSiteName());
    contentResource.setFirstCrawledNews(entity.getFirstCrawledNews());
    contentResource.setLastCrawledNews(entity.getLastCrawledNews());
    contentResource.setFirstUpdatedNews(entity.getFirstUpdatedNews());
    contentResource.setLastUpdatedNews(entity.getLastUpdatedNews());
    contentResource.setLastRead(entity.getLastRead());
    //contentResource.setLastStart(entity.getInitialDate());

    return contentResource;
  }

  @Override
  public CollectionModel<CrawlerInfoResource> toCollectionModel(Iterable<? extends CrawlerInfo> entities) {
    List<CrawlerInfoResource> resources = StreamSupport.stream(entities.spliterator(), false)
        .map(this::toModel)
        .collect(Collectors.toCollection(LinkedList::new));
    return CollectionModel.of(resources);
  }
}

