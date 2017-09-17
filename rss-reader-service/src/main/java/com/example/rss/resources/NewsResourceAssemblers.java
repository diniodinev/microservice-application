package com.example.rss.resources;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import com.example.rss.ProjectNameRestController;
import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.io.FeedException;

@Component
public class NewsResourceAssemblers extends ResourceAssemblerSupport<SyndEntry, NewsResource> {
    // From SyndEntry to NewsResource
    public NewsResourceAssemblers() {
        super(ProjectNameRestController.class, NewsResource.class);
    }

    @Override
    public NewsResource toResource(SyndEntry entity) {
        NewsResource resource = new NewsResource();
        resource.setTitle(entity.getTitle());
        resource.setUri(entity.getUri());

        try {
            resource.add(linkTo(methodOn(ProjectNameRestController.class).getDnesBgToday()).withSelfRel());
        } catch (IllegalArgumentException | FeedException | IOException e) {
            e.printStackTrace();
        }
        return resource;
    }

    @Override
    public List<NewsResource> toResources(Iterable<? extends SyndEntry> entities) {
        List<NewsResource> resources = new LinkedList<NewsResource>();
        for (SyndEntry entity : entities) {
            resources.add(toResource(entity));
        }

        return resources;
    }

}
