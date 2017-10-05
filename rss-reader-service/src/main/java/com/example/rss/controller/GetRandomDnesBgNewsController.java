package com.example.rss.controller;

import java.io.IOException;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.rss.repository.NewsRepository;
import com.example.rss.service.DnesBgParamEnum;
import com.example.rss.service.DnesBgParopertyReader;
import com.example.rss.service.ExtractionNewsService;

@RestController
@RequestMapping(value = "/dnesbg")
public class GetRandomDnesBgNewsController {

    @Autowired
    DnesBgParopertyReader dnesBgParopertyReader;

    @Autowired
    NewsRepository newsRepository;

    @Autowired
    ExtractionNewsService extractionNewsService;

    @RequestMapping(value = "/random")
    public void randomNews() throws IOException {
        Integer newsNumber = new Random().nextInt(Integer.valueOf(dnesBgParopertyReader.getProperties().get(DnesBgParamEnum.LAST.name()))) + 1;
        newsRepository.save(extractionNewsService.extractNews(dnesBgParopertyReader.getProperties(), newsNumber));
    }

    @RequestMapping(value = "/random/{number}")
    public void randomNews(@PathVariable("number") int newsCount) throws IOException {
        Integer newsNumber;
        for (int i = 0; i < newsCount; i++) {
            newsNumber = new Random().nextInt(Integer.valueOf(dnesBgParopertyReader.getProperties().get(DnesBgParamEnum.LAST.name()))) + 1;
            newsRepository.save(extractionNewsService.extractNews(dnesBgParopertyReader.getProperties(), newsNumber));
        }

    }
// http://www.dnes.bg/eu/2017/10/03/.23502
}
