package com.example.rss.service;

import java.io.IOException;

import com.example.rss.entity.News;

@FunctionalInterface
public interface ExtractionNewsService {

    News saveNews(Integer newsNumber) throws IOException;
}
