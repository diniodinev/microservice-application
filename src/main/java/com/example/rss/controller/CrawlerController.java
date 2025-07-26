package com.example.rss.controller;

import java.io.IOException;

import jakarta.servlet.http.HttpServletRequest;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.example.rss.controller.feed.AbstractController;
import com.example.rss.entity.CrawlerInfo;
import com.example.rss.repository.CrawlerInfoRepository;
import com.example.rss.service.CrawlerService;
import com.example.rss.web.assemblers.CrawlerInfoAssembler;
import com.example.rss.web.resources.CrawlerInfoResource;

@RefreshScope
@RestController
public class CrawlerController extends AbstractController {

    /** Logger. */
    private static final Logger LOG = LoggerFactory.getLogger(CrawlerController.class);

    private static final String DNESBG_NAME = "dnesbg";

    @Autowired
    private CrawlerService dnesBgCrawler;

    @Autowired
    private CrawlerInfoRepository crawlerInfoRepository;

    @Autowired
    private CrawlerInfoAssembler crawlerInfoAssembler;

    private RestTemplate restTemplate;

    public CrawlerController(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    @RequestMapping(value = "/dnesbgcontinue/{number}", method = RequestMethod.GET)
    public CrawlerInfoResource continueReading(HttpServletRequest request,
            @PathVariable int number)
            throws IOException {
        CrawlerInfo dnesBgCrawlerInfo = dnesBgCrawler.getSiteName(DNESBG_NAME);
        if (dnesBgCrawlerInfo == null) {
            return new CrawlerInfoResource();
        }

        dnesBgCrawlerInfo.setInitialDate(DateTime.now());
        int startNews = dnesBgCrawlerInfo.getLastRead();

        for (int i = 1; i <= number; i++) {
          //  queueService.enqueue(String.valueOf(i + startNews));
            dnesBgCrawlerInfo.setLastRead(i + startNews);
            LOG.info("News with id {} sent to the queue.", (i + startNews));
        }
        return crawlerInfoAssembler.toModel(crawlerInfoRepository.save(dnesBgCrawlerInfo));

    }

}
