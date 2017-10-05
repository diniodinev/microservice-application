package com.example.rss.service;

import java.io.IOException;
import java.util.Map;

import com.example.rss.entity.News;

@FunctionalInterface
public interface ExtractionNewsService {

    News extractNews(Map<String, String> params, int number) throws IOException;
}
