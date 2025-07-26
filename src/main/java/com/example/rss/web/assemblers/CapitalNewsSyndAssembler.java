package com.example.rss.web.assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.stereotype.Component;

import com.example.rss.controller.feed.CapitalFeedController;
import com.example.rss.web.resources.NewsResource;
import com.rometools.rome.feed.synd.SyndEntry;

@Component
public class CapitalNewsSyndAssembler extends BaseNewsSyndAssembler {

    @Override
    public NewsResource toModel(SyndEntry entity) {
        NewsResource newsResource = super.toModel(entity);
        newsResource.setUri(entity.getLink());

        newsResource.add(linkTo(methodOn(CapitalFeedController.class).getCapitalBulgaria()).withSelfRel());

        return newsResource;
    }
}
