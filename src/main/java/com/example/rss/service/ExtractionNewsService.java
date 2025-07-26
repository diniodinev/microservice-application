package com.example.rss.service;

import java.util.List;

import com.example.rss.core.BaseNewsHtmlPage;
import com.example.rss.entity.Author;
import com.example.rss.entity.Content;
import com.example.rss.entity.Image;
import com.example.rss.entity.News;
import com.example.rss.repository.AuthorRepository;
import com.example.rss.utils.tags.ContentTags;
import com.example.rss.utils.tags.ImageTags;

public interface ExtractionNewsService {

    News saveNews(Integer newsNumber);

    News extractNews(Integer number);

    List<Image> extractSlideShowImages(BaseNewsHtmlPage page, ImageTags imageTags);

    Content extractContent(BaseNewsHtmlPage parsedPage, ContentTags contentTags, ImageTags imageTags);

    Author extractAuthor(BaseNewsHtmlPage page, AuthorRepository authorRepository, String author);

    String constructImageUrl(BaseNewsHtmlPage page, ImageTags imageTags);

	News extractNews(News oldNews, BaseNewsHtmlPage page, Content newsContent, Author newsAuthor);

}
