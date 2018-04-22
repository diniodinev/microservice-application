package com.example.rss.web.assemblers;

import java.util.LinkedList;
import java.util.List;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

import com.example.rss.web.resources.NewsResource;
import com.rometools.rome.feed.synd.SyndEntry;

public class BaseNewsSyndAssembler extends ResourceAssemblerSupport<SyndEntry, NewsResource>{
    public BaseNewsSyndAssembler() {
        super(SyndEntry.class, NewsResource.class);
    }

    @Override
    public NewsResource toResource(SyndEntry entity) {
        NewsResource resource = new NewsResource();
        resource.setTitle(entity.getTitle());
        resource.setUri(entity.getUri());

        // resource.add(linkTo(methodOn(BbcController.class).getBbcTopStories()).withSelfRel());

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
