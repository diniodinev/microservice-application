package com.example.rss.web.assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.example.rss.controller.feed.DnesBgFeedController;
import com.example.rss.web.resources.NewsResource;
import com.rometools.rome.feed.synd.SyndEntry;

@Component
public class DnesbgNewsResourceAssemblers implements RepresentationModelAssembler<SyndEntry, NewsResource> {

    @Override
    public NewsResource toModel(SyndEntry entity) {
        NewsResource resource = new NewsResource();
        resource.setTitle(entity.getTitle());
        resource.setUri(entity.getUri());

        resource.add(linkTo(methodOn(DnesBgFeedController.class).getDnesBgToday()).withSelfRel());
        return resource;
    }

    @Override
    public CollectionModel<NewsResource> toCollectionModel(Iterable<? extends SyndEntry> entities) {
        List<NewsResource> resources = StreamSupport.stream(entities.spliterator(), false)
            .map(this::toModel)
            .collect(Collectors.toCollection(LinkedList::new));

        return CollectionModel.of(resources);
    }
}
