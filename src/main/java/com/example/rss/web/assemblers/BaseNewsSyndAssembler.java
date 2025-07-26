package com.example.rss.web.assemblers;

import com.example.rss.web.resources.NewsResource;
import com.rometools.rome.feed.synd.SyndEntry;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class BaseNewsSyndAssembler implements RepresentationModelAssembler<SyndEntry, NewsResource> {

    @Override
    public NewsResource toModel(SyndEntry entity) {
        NewsResource resource = new NewsResource();
        resource.setTitle(entity.getTitle());
        resource.setUri(entity.getUri());

        // Optional: add HATEOAS links here
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
