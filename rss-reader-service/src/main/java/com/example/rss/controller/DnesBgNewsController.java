package com.example.rss.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

import org.hsqldb.lib.tar.TarMalformatException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.rss.config.DnesbgProperties;
import com.example.rss.controller.feed.AbstractController;
import com.example.rss.entity.News;
import com.example.rss.service.CommentsService;
import com.example.rss.service.ExtractionNewsService;
import com.example.rss.utils.DnesBgParamEnum;
import com.example.rss.web.assemblers.DetailsNewsAssembler;
import com.example.rss.web.resources.DetailsNewsResource;

@RestController
public class DnesBgNewsController extends AbstractController {

    @Autowired
    private ExtractionNewsService dnesbgExtractionNewsServiceImpl;

    @Autowired
    private CommentsService commentsService;

    @Autowired
    private DnesbgProperties serviceProperties;

    @Autowired
    private DetailsNewsAssembler detailsNewsAssembler;

    @RequestMapping(value = "/dnesbg/random", method = RequestMethod.GET)
    public DetailsNewsResource randomNews() throws SQLException, IOException, TarMalformatException {
        Integer newsNumber = new Random()
                .nextInt(Integer.valueOf(serviceProperties.getDnesbg().get(DnesBgParamEnum.last.name()))) + 1;
        News saved = dnesbgExtractionNewsServiceImpl.saveNews(newsNumber);
        if (saved != null) {
            commentsService.extractComments(newsNumber, saved.getId());
        }
        return detailsNewsAssembler.toResource(saved);
    }

    @RequestMapping(value = "/dnesbg/random/{number}", method = RequestMethod.GET)
    public List<DetailsNewsResource> randomNews(@PathVariable("number") int newsCount) {
        List<News> allNews = new LinkedList<>();
        IntStream.range(0, newsCount).forEach(i -> {
            Integer newsNumber;
            newsNumber = new Random()
                    .nextInt(Integer.valueOf(serviceProperties.getDnesbg().get(DnesBgParamEnum.last.name()))) + 1;

            News saved;
            saved = dnesbgExtractionNewsServiceImpl.saveNews(newsNumber);
            if (saved != null) {
                allNews.add(saved);
                commentsService.extractComments(newsNumber, saved.getId());
            }
        });
        return detailsNewsAssembler.toResources(allNews);
    }

    @RequestMapping(value = "/dnesbg/last/{number}", method = RequestMethod.GET)
    public List<DetailsNewsResource> lastN(@PathVariable("number") int newsCount) {
        List<News> allNews = new LinkedList<>();
        Integer newsNumber;
        for (int i = 0; i < newsCount; i++) {
            newsNumber = Integer.valueOf(serviceProperties.getDnesbg().get(DnesBgParamEnum.last.name())) + 1 - i;

            News saved = dnesbgExtractionNewsServiceImpl.saveNews(newsNumber);
            if (saved != null) {
                allNews.add(saved);
                commentsService.extractComments(newsNumber, saved.getId());
            }
        }
        return detailsNewsAssembler.toResources(allNews);
    }

    @RequestMapping(value = "/dnesbg/{id}", method = RequestMethod.GET)
    public DetailsNewsResource getSpecificNews(@PathVariable("id") int newsId) {
        News saved = dnesbgExtractionNewsServiceImpl.saveNews(newsId);
        if (saved != null) {
            commentsService.extractComments(newsId, saved.getId());
        }
        return detailsNewsAssembler.toResource(saved);
    }

}
