package com.example.rss.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.example.rss.controller.feed.AbstractController;
import com.example.rss.entity.News;
import com.example.rss.service.ExtractionNewsService;
import com.example.rss.web.assemblers.DetailsNewsAssembler;
import com.example.rss.web.resources.DetailsNewsResource;

@RestController
public class CapitalNewsController extends AbstractController {

    @Autowired
    private DetailsNewsAssembler detailsNewsAssembler;

    @Autowired
    private ExtractionNewsService capitalExtractionNewsServiceImpl;

    @GetMapping(value = "/capital/{id}")
    public DetailsNewsResource getSpecificNews(@PathVariable("id") int newsId) {
        News saved = capitalExtractionNewsServiceImpl.saveNews(newsId);
        /*
         * if (saved != null) { commentsService.extractComments(newsId,
         * saved.getId()); }
         */
        return detailsNewsAssembler.toModel(saved);
    }

}
