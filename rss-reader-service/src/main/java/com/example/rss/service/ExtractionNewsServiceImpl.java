package com.example.rss.service;

import java.io.IOException;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.example.rss.core.ProcessDnesBgHtmlPage;
import com.example.rss.entity.Content;
import com.example.rss.entity.News;

@Service
public class ExtractionNewsServiceImpl implements ExtractionNewsService {

    @Override
    public News extractNews(Map<String, String> params, int number) throws IOException {
        String newsUrl = params.get(DnesBgParamEnum.ROOTURL.name()) + number;

        ProcessDnesBgHtmlPage page = new ProcessDnesBgHtmlPage(newsUrl);
        Content newsContent = new Content();
        newsContent.setNewsDescriptin(page.extractInformationByTag(params.get(DnesBgParamEnum.DESCRIPTION.name())));
        newsContent.setNewsContent(page.extractInformationByTag(params.get(DnesBgParamEnum.CONTENT.name())));

        News newsToSave = new News();
        newsToSave.setTitle(page.extractInformationByTag(params.get(DnesBgParamEnum.TITLE.name())));
        newsToSave.setNewsContant(newsContent);
        newsToSave.setUri(newsUrl);
        return newsToSave;
    }

}
