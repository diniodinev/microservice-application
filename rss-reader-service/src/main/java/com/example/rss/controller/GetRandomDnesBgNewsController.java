package com.example.rss.controller;

import java.io.IOException;
import java.util.Random;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.rss.config.DnesbgProperties;
import com.example.rss.entity.News;
import com.example.rss.repository.CommentsRepository;
import com.example.rss.repository.NewsRepository;
import com.example.rss.service.CommentsService;
import com.example.rss.service.DnesBgParamEnum;
import com.example.rss.service.ExtractionNewsService;

@RestController
public class GetRandomDnesBgNewsController extends AbstractController {

    @Autowired
    private NewsRepository newsRepository;

    @Autowired
    private CommentsRepository commentsRepository;

    @Autowired
    private ExtractionNewsService extractionNewsService;

    @Autowired
    private CommentsService commentsService;

    @Autowired
    private DnesbgProperties serviceProperties;

    @RequestMapping(value = "/dnesbg/random", method = RequestMethod.GET)
    public void randomNews() throws IOException {
        Integer newsNumber = new Random()
                .nextInt(Integer.valueOf(serviceProperties.getDnesbg().get(DnesBgParamEnum.last.name()))) + 1;
        News saved = extractionNewsService.saveNews(newsNumber);
        if (saved != null) {
            commentsService.extractComments(newsNumber);
        }
    }

    @RequestMapping(value = "/dnesbg/random/{number}", method = RequestMethod.GET)
    public void randomNews(@PathVariable("number") int newsCount) throws IOException {

        IntStream.range(0, newsCount).parallel().forEach(i -> {
            Integer newsNumber;
            newsNumber = new Random()
                    .nextInt(Integer.valueOf(serviceProperties.getDnesbg().get(DnesBgParamEnum.last.name()))) + 1;

            News saved;
            saved = extractionNewsService.saveNews(newsNumber);
            if (saved != null) {
                commentsService.extractComments(newsNumber);
            }
        });
    }

    @RequestMapping(value = "/dnesbg/last/{number}", method = RequestMethod.GET)
    public void lastN(@PathVariable("number") int newsCount) {
        Integer newsNumber;
        for (int i = 0; i < newsCount; i++) {
            newsNumber = Integer.valueOf(serviceProperties.getDnesbg().get(DnesBgParamEnum.last.name())) + 1 - i;

            News saved = extractionNewsService.saveNews(newsNumber);
            if (saved != null) {
                commentsService.extractComments(newsNumber);
            }
        }
    }

    @RequestMapping(value = "/dnesbg/{id}", method = RequestMethod.GET)
    public void getSpecificNews(@PathVariable("id") int newsId) throws IOException {
        News saved = extractionNewsService.saveNews(newsId);
        if (saved != null) {
            commentsService.extractComments(newsId);
        }
    }

}
