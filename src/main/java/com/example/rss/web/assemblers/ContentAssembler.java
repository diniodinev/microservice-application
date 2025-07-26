package com.example.rss.web.assemblers;

import com.example.rss.entity.Content;
import com.example.rss.web.resources.ContentResource;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Component
public class ContentAssembler implements RepresentationModelAssembler<Content, ContentResource> {

  @Override
  public ContentResource toModel(Content entity) {
    ContentResource contentResource = new ContentResource();
    contentResource.setNewsContent(entity.getNewsContent());
    contentResource.setNewsDescriptin(entity.getNewsDescription());

    return contentResource;
  }

  @Override
  public CollectionModel<ContentResource> toCollectionModel(Iterable<? extends Content> entities) {
    return CollectionModel.of(
        StreamSupport.stream(entities.spliterator(), false)
            .map(this::toModel)
            .collect(Collectors.toList())
    );
  }
}
