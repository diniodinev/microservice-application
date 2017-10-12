package com.example.rss.service;

import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.rss.core.ProcessDnesBgHtmlPage;
import com.example.rss.entity.Author;
import com.example.rss.entity.Content;
import com.example.rss.entity.News;
import com.example.rss.repository.AuthorRepository;

@Service
public class ExtractionNewsServiceImpl implements ExtractionNewsService {

    @Autowired
    private AuthorRepository authorRepository;

    @Override
    public News extractNews(Map<String, String> params, int number) throws IOException {
        String newsUrl = params.get(DnesBgParamEnum.rootUrl.name()) + number;
        ProcessDnesBgHtmlPage page = new ProcessDnesBgHtmlPage(newsUrl);
        if (page.getDocument() == null) {
            return null;
        }
        Content newsContent = new Content();
        newsContent.setNewsDescriptin(
                page.extractInformationByTag(params.get(DnesBgParamEnum.descriptionSelector.name())));
        newsContent.setNewsContent(page.extractInformationByTag(params.get(DnesBgParamEnum.contentSelector.name())));

        String authorNames = page.extractInformationByTag(params.get(DnesBgParamEnum.authorSelector.name()));
        Author newsAuthor = authorRepository.findOneByNames(authorNames);

        if (newsAuthor == null) {
            newsAuthor = new Author();
            newsAuthor.setNames(authorNames);
        }

        News newsToSave = new News();
        newsToSave.setTitle(page.extractInformationByTag(params.get(DnesBgParamEnum.titleSelector.name())));
        newsToSave.setNewsContant(newsContent);
        newsToSave.setUri(newsUrl);
        newsToSave.setAuthor(newsAuthor);

        newsContent.setNews(newsToSave);
        return newsToSave;
    }

}
