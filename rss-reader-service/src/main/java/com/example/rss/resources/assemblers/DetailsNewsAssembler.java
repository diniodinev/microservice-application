package com.example.rss.resources.assemblers;

import java.util.List;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import com.example.rss.entity.News;
import com.example.rss.resources.DetailsNewsResource;

@Component
public class DetailsNewsAssembler extends ResourceAssemblerSupport<News, DetailsNewsResource> {

    public DetailsNewsAssembler() {
        super(News.class, DetailsNewsResource.class);
    }

    @Override
    public DetailsNewsResource toResource(News entity) {
        DetailsNewsResource detailsNewsResource = new DetailsNewsResource();
        detailsNewsResource.setTitle(entity.getTitle());
        detailsNewsResource.setUri(entity.getUri());
        return detailsNewsResource;
    }

    @Override
    public List<DetailsNewsResource> toResources(Iterable<? extends News> entities) {
        // TODO Auto-generated method stub
        return super.toResources(entities);
    }

}
