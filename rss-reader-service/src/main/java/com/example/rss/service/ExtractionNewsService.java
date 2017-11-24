package com.example.rss.service;

import java.util.List;

import com.example.rss.core.ProcessDnesBgHtmlPage;
import com.example.rss.entity.Image;
import com.example.rss.entity.News;

public interface ExtractionNewsService {

    News saveNews(Integer newsNumber);

    News extractNews(int number);

    List<Image> extractSlideShowImages(ProcessDnesBgHtmlPage page);
}
