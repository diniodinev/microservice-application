package com.example.rss.web.assemblers;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.springframework.stereotype.Component;

import com.example.rss.controller.feed.CapitalFeedController;
import com.example.rss.web.resources.NewsResource;
import com.rometools.rome.feed.synd.SyndEntry;

@Component
public class CapitalNewsSyndAssembler extends BaseNewsSyndAssembler {

    @Override
    public NewsResource toResource(SyndEntry entity) {
        NewsResource newsResource = super.toResource(entity);
        newsResource.setUri(entity.getLink());

        newsResource
                .add(linkTo(methodOn(CapitalFeedController.class).getCapitalBulgaria()).withSelfRel());

        return newsResource;
    }

}
