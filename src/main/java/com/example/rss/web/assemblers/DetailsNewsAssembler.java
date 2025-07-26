package com.example.rss.web.assemblers;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.example.rss.entity.News;
import com.example.rss.web.resources.DetailsNewsResource;

@Component
public class DetailsNewsAssembler implements RepresentationModelAssembler<News, DetailsNewsResource> {

    @Autowired
    private AuthorAssembler authorAssembler;

    @Autowired
    private ContentAssembler contentAssembler;

    @Override
    public DetailsNewsResource toModel(News entity) {
        if (entity == null) {
            return null;
        }

        DetailsNewsResource detailsNewsResource = new DetailsNewsResource();
        detailsNewsResource.setTitle(entity.getTitle());
        detailsNewsResource.setUri(entity.getUri());

        detailsNewsResource.setAuthorResource(authorAssembler.toModel(entity.getAuthor()));
        detailsNewsResource.setNews(contentAssembler.toModel(entity.getNewsContent()));

        detailsNewsResource.setInitialDate(Date.from(entity.getInitialDate()));

        return detailsNewsResource;
    }

    @Override
    public CollectionModel<DetailsNewsResource> toCollectionModel(Iterable<? extends News> entities) {
        if (entities == null) {
            return CollectionModel.empty();
        }

        List<DetailsNewsResource> resources = StreamSupport.stream(entities.spliterator(), false)
            .map(this::toModel)
            .collect(Collectors.toCollection(LinkedList::new));

        return CollectionModel.of(resources);
    }
}
