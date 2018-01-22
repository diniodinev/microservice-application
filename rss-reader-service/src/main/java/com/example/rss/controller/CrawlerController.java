package com.example.rss.controller;

import java.io.IOException;
import java.io.Writer;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.example.rss.entity.CrawlerInfo;
import com.example.rss.repository.CrawlerInfoRepository;
import com.example.rss.service.CrawlerService;

@Controller
@RefreshScope
public class CrawlerController extends AbstractController {

    /** Logger. */
    private static final Logger LOG = LoggerFactory.getLogger(CrawlerController.class);

    private static final String DNESBG_NAME = "dnesbg";

    @Autowired
    private CrawlerService dnesBgCrawler;

    @Autowired
    CrawlerInfoRepository crawlerInfoRepository;

    private RestTemplate restTemplate;

    public CrawlerController(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    @RequestMapping(value = "/dnesbgcontinue/{number}", method = RequestMethod.GET)
    @ResponseBody
    public void continueReading(Writer writer, HttpServletRequest request, @PathVariable int number)
            throws IOException {
        CrawlerInfo dnesBgCrawlerInfo = dnesBgCrawler.getSiteName(DNESBG_NAME);
        dnesBgCrawlerInfo.setInitialDate(DateTime.now());
        if (dnesBgCrawlerInfo == null) {
            writer.append("Site Crawler cannot be started. No entry in the DB for the key " + DNESBG_NAME);
        }
        int startNews = dnesBgCrawlerInfo.getLastRead();

        for (int i = 1; i <= number; i++) {
            DnesBgNewsController companyInfoResponse = restTemplate.getForObject(
                    StringUtils.substringBeforeLast(request.getRequestURL().toString(), "/dnesbgcontinue") + "/dnesbg/"
                            + (i + startNews),
                    DnesBgNewsController.class);

            dnesBgCrawlerInfo.setLastRead(i + startNews);
        }
        crawlerInfoRepository.save(dnesBgCrawlerInfo);
        /*
         * if (lastCrawledNewsId == 0) { lastCrawledNewsId =
         * (Integer.valueOf(serviceProperties.getDnesbg().get(DnesBgParamEnum.
         * last.name()))); } return lastCrawledNewsId;
         */

    }

}
