package com.example.rss.web.assemblers;

import java.util.LinkedList;
import java.util.List;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import com.example.rss.entity.Content;
import com.example.rss.web.resources.ContentResource;

@Component
public class ContentAssembler extends ResourceAssemblerSupport<Content, ContentResource> {

    public ContentAssembler() {
        super(Content.class, ContentResource.class);
    }

    @Override
    public List<ContentResource> toResources(Iterable<? extends Content> entities) {
        List<ContentResource> resources = new LinkedList<>();
        for (Content content : entities) {
            resources.add(toResource(content));
        }
        return resources;
    }

    @Override
    public ContentResource toResource(Content entity) {
        ContentResource contentResource = new ContentResource();
        contentResource.setNewsContent(entity.getNewsContent());
        contentResource.setNewsDescriptin(entity.getNewsDescriptin());

        return contentResource;
    }
}
