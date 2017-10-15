package com.example.rss.controller;

import java.io.IOException;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.rss.config.DnesbgProperties;
import com.example.rss.entity.News;
import com.example.rss.repository.CommentsRepository;
import com.example.rss.repository.NewsRepository;
import com.example.rss.service.CommentsService;
import com.example.rss.service.DnesBgParamEnum;
import com.example.rss.service.ExtractionNewsService;

@RestController
@RequestMapping(value = "/dnesbg")
public class GetRandomDnesBgNewsController {

    @Autowired
    NewsRepository newsRepository;

    @Autowired
    CommentsRepository commentsRepository;

    @Autowired
    ExtractionNewsService extractionNewsService;

    @Autowired
    CommentsService commentsService;

    @Autowired
    private DnesbgProperties serviceProperties;

    @RequestMapping(value = "/random")
    public void randomNews() throws IOException {
        Integer newsNumber = new Random()
                .nextInt(Integer.valueOf(serviceProperties.getDnesbg().get(DnesBgParamEnum.last.name()))) + 1;
        extractionNewsService.saveNews(newsNumber);
    }

    @RequestMapping(value = "/random/{number}")
    public void randomNews(@PathVariable("number") int newsCount) throws IOException {
        Integer newsNumber;
        for (int i = 0; i < newsCount; i++) {
            newsNumber = new Random()
                    .nextInt(Integer.valueOf(serviceProperties.getDnesbg().get(DnesBgParamEnum.last.name()))) + 1;

            News saved = extractionNewsService.saveNews(newsNumber);
            commentsService.extractComments(saved);
        }
    }

}
