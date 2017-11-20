package com.example.rss.resources.assemblers;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.util.LinkedList;
import java.util.List;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import com.example.rss.controller.BbcController;
import com.example.rss.resources.NewsResource;
import com.rometools.rome.feed.synd.SyndEntry;

@Component
public class BbcNewsResourceAssemblers extends ResourceAssemblerSupport<SyndEntry, NewsResource> {
    // From SyndEntry to NewsResource
    public BbcNewsResourceAssemblers() {
        super(BbcController.class, NewsResource.class);
    }

    @Override
    public NewsResource toResource(SyndEntry entity) {
        NewsResource resource = new NewsResource();
        resource.setTitle(entity.getTitle());
        resource.setUri(entity.getUri());

        resource.add(linkTo(methodOn(BbcController.class).getBbcTopStories()).withSelfRel());

        return resource;
    }

    @Override
    public List<NewsResource> toResources(Iterable<? extends SyndEntry> entities) {
        List<NewsResource> resources = new LinkedList<>();
        for (SyndEntry entity : entities) {
            resources.add(toResource(entity));
        }

        return resources;
    }

}
