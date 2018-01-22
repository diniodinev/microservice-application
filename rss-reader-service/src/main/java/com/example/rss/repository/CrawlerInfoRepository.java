package com.example.rss.repository;

import org.springframework.data.repository.CrudRepository;

import com.example.rss.entity.CrawlerInfo;

public interface CrawlerInfoRepository extends CrudRepository<CrawlerInfo, Long> {

    CrawlerInfo findOneByCrawledSiteName(String crawledSiteName);

}
