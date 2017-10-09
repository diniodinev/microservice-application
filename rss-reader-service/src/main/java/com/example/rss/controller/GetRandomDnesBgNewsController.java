package com.example.rss.controller;

import java.io.IOException;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.rss.config.DnesbgProperties;
import com.example.rss.entity.News;
import com.example.rss.repository.NewsRepository;
import com.example.rss.service.DnesBgParamEnum;
import com.example.rss.service.ExtractionNewsService;

@RestController
@RequestMapping(value = "/dnesbg")
public class GetRandomDnesBgNewsController {

    @Autowired
    NewsRepository newsRepository;

    @Autowired
    ExtractionNewsService extractionNewsService;

    @Autowired
    private DnesbgProperties serviceProperties;

    @RequestMapping(value = "/random")
    public void randomNews() throws IOException {
        Integer newsNumber = new Random()
                .nextInt(Integer.valueOf(serviceProperties.getDnesbg().get(DnesBgParamEnum.last.name()))) + 1;
        saveNews(newsNumber);
    }

    private void saveNews(Integer newsNumber) throws IOException {
        News news = extractionNewsService.extractNews(serviceProperties.getDnesbg(), newsNumber);
        if (news != null) {
            newsRepository.save(news);
        }
    }

    @RequestMapping(value = "/random/{number}")
    public void randomNews(@PathVariable("number") int newsCount) throws IOException {
        Integer newsNumber;
        for (int i = 0; i < newsCount; i++) {
            newsNumber = new Random()
                    .nextInt(Integer.valueOf(serviceProperties.getDnesbg().get(DnesBgParamEnum.last.name()))) + 1;
            
            saveNews(newsNumber);
        }

    }
    // http://www.dnes.bg/eu/2017/10/03/.23502
}
