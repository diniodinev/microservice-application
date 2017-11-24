package com.example.rss.service;

import java.util.List;

import com.example.rss.core.BaseNewsHtmlPage;
import com.example.rss.entity.Image;
import com.example.rss.entity.News;

public interface ExtractionNewsService {

    News saveNews(Integer newsNumber);

    News extractNews(Integer number);

    List<Image> extractSlideShowImages(BaseNewsHtmlPage page);
}
