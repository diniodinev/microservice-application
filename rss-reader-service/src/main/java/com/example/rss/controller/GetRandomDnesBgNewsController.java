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
        News saved = extractionNewsService.saveNews(newsNumber);
        if (saved != null) {
            commentsService.extractComments(saved);
        }
    }

    @RequestMapping(value = "/random/{number}")
    public void randomNews(@PathVariable("number") int newsCount) throws IOException {
        Integer newsNumber;
        for (int i = 0; i < newsCount; i++) {
            newsNumber = new Random()
                    .nextInt(Integer.valueOf(serviceProperties.getDnesbg().get(DnesBgParamEnum.last.name()))) + 1;

            News saved = extractionNewsService.saveNews(newsNumber);
            if (saved != null) {
                commentsService.extractComments(saved);
            }
        }
    }

    @RequestMapping(value = "/last/{number}")
    public void lastN(@PathVariable("number") int newsCount) throws IOException {
        Integer newsNumber;
        for (int i = 0; i < newsCount; i++) {
            newsNumber = Integer.valueOf(serviceProperties.getDnesbg().get(DnesBgParamEnum.last.name())) + 1 - i;

            News saved = extractionNewsService.saveNews(newsNumber);
            if (saved != null) {
                commentsService.extractComments(saved);
            }
        }
    }

    @RequestMapping(value = "/{id}")
    public void getSpecificNews(@PathVariable("id") int newsId) throws IOException {
        News saved = extractionNewsService.saveNews(newsId);
        if (saved != null) {
            commentsService.extractComments(saved);
        }
    }

}
