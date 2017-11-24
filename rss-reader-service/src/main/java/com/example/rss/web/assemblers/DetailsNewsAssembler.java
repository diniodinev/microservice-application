package com.example.rss.web.assemblers;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import com.example.rss.entity.News;
import com.example.rss.web.resources.DetailsNewsResource;

@Component
public class DetailsNewsAssembler extends ResourceAssemblerSupport<News, DetailsNewsResource> {

    @Autowired
    AuthorAssembler authorAssembler;

    @Autowired
    ContentAssembler contentAssembler;

    public DetailsNewsAssembler() {
        super(News.class, DetailsNewsResource.class);
    }

    @Override
    public DetailsNewsResource toResource(News entity) {
        if (entity == null) {
            return null;
        }
        DetailsNewsResource detailsNewsResource = new DetailsNewsResource();
        detailsNewsResource.setTitle(entity.getTitle());
        detailsNewsResource.setUri(entity.getUri());

        detailsNewsResource.setAuthorResource(authorAssembler.toResource(entity.getAuthor()));
        detailsNewsResource.setNewsContant(contentAssembler.toResource(entity.getNewsContant()));

        detailsNewsResource.setInitialDate(entity.getInitialDate().toDate());
        return detailsNewsResource;
    }

    @Override
    public List<DetailsNewsResource> toResources(Iterable<? extends News> entities) {
        if (entities == null) {
            return null;
        }
        List<DetailsNewsResource> resources = new LinkedList<>();
        for (News news : entities) {
            resources.add(toResource(news));
        }
        return resources;
    }

}
