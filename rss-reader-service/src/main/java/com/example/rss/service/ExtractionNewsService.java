package com.example.rss.service;

import com.example.rss.entity.News;

@FunctionalInterface
public interface ExtractionNewsService {

    News saveNews(Integer newsNumber);
}
