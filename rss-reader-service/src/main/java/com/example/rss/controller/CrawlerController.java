package com.example.rss.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.rss.repository.CrawlerInfoRepository;
import com.example.rss.service.CrawlerService;

@Controller
@RefreshScope
public class CrawlerController extends AbstractController {

    @Autowired
    private CrawlerService crawlerService;

    @RequestMapping(value = "/continue", method = RequestMethod.GET)
    @ResponseBody
    public void continueReading() {
        int startPosition = crawlerService.getLast();

    }

}
