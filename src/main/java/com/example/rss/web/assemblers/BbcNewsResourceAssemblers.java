package com.example.rss.web.assemblers;

import com.example.rss.controller.feed.BbcFeedController;
import com.example.rss.web.resources.NewsResource;
import com.rometools.rome.feed.synd.SyndEntry;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class BbcNewsResourceAssemblers implements RepresentationModelAssembler<SyndEntry, NewsResource> {

    @Override
    public NewsResource toModel(SyndEntry entity) {
        NewsResource resource = new NewsResource();
        resource.setTitle(entity.getTitle());
        resource.setUri(entity.getUri());

        resource.add(linkTo(methodOn(BbcFeedController.class).getBbcTopStories()).withSelfRel());

        return resource;
    }

    @Override
    public CollectionModel<NewsResource> toCollectionModel(Iterable<? extends SyndEntry> entities) {
        List<NewsResource> resources = StreamSupport.stream(entities.spliterator(), false)
            .map(this::toModel)
            .collect(Collectors.toList());

        return CollectionModel.of(resources);
    }
}
