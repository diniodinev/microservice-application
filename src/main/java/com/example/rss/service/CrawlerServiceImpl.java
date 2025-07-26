package com.example.rss.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.rss.entity.CrawlerInfo;
import com.example.rss.repository.CrawlerInfoRepository;

@Service
public class CrawlerServiceImpl implements CrawlerService {

    private static final Logger LOG = LoggerFactory.getLogger(CrawlerServiceImpl.class);

    @Autowired
    private CrawlerInfoRepository crawlerRepository;

    @Override
    public CrawlerInfo getSiteName(String siteName) {
        CrawlerInfo lastCrawledNewsId = crawlerRepository.findOneByCrawledSiteName(siteName);

        if (lastCrawledNewsId == null) {
            LOG.warn("Site Crawler cannot be started. No entry in the DB for the key {}", siteName);
            return null;
        }
        return lastCrawledNewsId;
    }


}
