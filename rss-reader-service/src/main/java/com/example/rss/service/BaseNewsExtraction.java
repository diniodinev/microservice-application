package com.example.rss.service;

import org.apache.commons.lang.StringUtils;

import com.example.rss.core.BaseNewsHtmlPage;
import com.example.rss.entity.Author;
import com.example.rss.entity.Content;
import com.example.rss.reading.ReadingPage;
import com.example.rss.repository.AuthorRepository;
import com.example.rss.utils.tags.ContentTags;

public abstract class BaseNewsExtraction implements ExtractionNewsService {

    public BaseNewsHtmlPage parsePage(Integer number, ReadingPage pageReader) {
        BaseNewsHtmlPage page = pageReader.getPage(number);
        return page;
    }

    @Override
    public Content extractContent(BaseNewsHtmlPage parsedPage, ContentTags contentTags) {
        Content newsContent = new Content();
        newsContent.setNewsDescription(parsedPage.extractInformationByTag(contentTags.getDescriptionTag()));
        newsContent.setNewsContent(parsedPage.extractInformationByTag(contentTags.getContentTag()));
        newsContent.setImages(extractSlideShowImages(parsedPage));

        return newsContent;
    }

    @Override
    public Author extractAuthor(BaseNewsHtmlPage page, AuthorRepository authorRepository, String author) {

        Author newsAuthor = authorRepository.findOneByNames(author);
        if (newsAuthor == null) {
            newsAuthor = new Author();
            newsAuthor.setNames(author);
        }
        return newsAuthor;
    }

}
