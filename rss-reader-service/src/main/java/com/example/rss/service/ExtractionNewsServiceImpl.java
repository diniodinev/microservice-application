package com.example.rss.service;

import java.io.IOException;
import java.util.Locale;

import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.rss.core.ProcessDnesBgHtmlPage;
import com.example.rss.entity.Author;
import com.example.rss.entity.Content;
import com.example.rss.entity.News;
import com.example.rss.reading.ReadingDnesBgPage;
import com.example.rss.repository.AuthorRepository;
import com.example.rss.repository.NewsRepository;
import com.example.rss.utils.CustomDateUtils;
import com.example.rss.utils.DnesBgParams;

@Service
public class ExtractionNewsServiceImpl implements ExtractionNewsService {

    private static final Logger logger = LoggerFactory.getLogger(ExtractionNewsServiceImpl.class);

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private NewsRepository newsRepository;

    @Autowired
    private ReadingDnesBgPage readingDnesBgPage;

    @Autowired
    private DnesBgParams params;

    @Override
    public News saveNews(Integer newsNumber) throws IOException {
        News news = extractNews(newsNumber);
        if (news != null) {
            newsRepository.save(news);
        }
        return news;
    }

    private News extractNews(int number) throws IOException {
        String newsUrl = readingDnesBgPage.getUrl(number);
        ProcessDnesBgHtmlPage page = readingDnesBgPage.getPage(number);

        Content newsContent = new Content();
        newsContent.setNewsDescriptin(page.extractInformationByTag(params.getDescription()));
        newsContent.setNewsContent(page.extractInformationByTag(params.getContent()));

        String authorNames = page.extractInformationByTag(params.getAuthor());
        Author newsAuthor = authorRepository.findOneByNames(authorNames);

        if (newsAuthor == null) {
            newsAuthor = new Author();
            newsAuthor.setNames(authorNames);
        }

        // News information
        News newsToSave = new News();
        newsToSave.setTitle(page.extractInformationByTag(params.getTitle()));
        newsToSave.setNewsContant(newsContent);
        newsToSave.setUri(newsUrl);
        newsToSave.setAuthor(newsAuthor);
        newsToSave.setInitialDate(extractNewsCreatedDate(page.extractInformationByTag(params.getCreatedDate())));

        newsContent.setNews(newsToSave);
        return newsToSave;
    }

    // Contains Specific DnesBgLogic
    private DateTime extractNewsCreatedDate(String date) {
        logger.debug("Input date before transformation {}", date);
        DateTime createdDateTime = new DateTime();
        Locale locale = new java.util.Locale("bg", "BG");
        // Replace 3 letters months with theri full representation in order JODA
        // to parse them.
        date = CustomDateUtils.replaceWithFullMonthName(date);
        DateTimeFormatter dtf = DateTimeFormat.forPattern("dd MMM yyyy HH:mm").withLocale(locale);

        if (date != null) {
            if (!StringUtils.contains(date, '|')) {
                createdDateTime = dtf.parseDateTime(date.subSequence(0, date.indexOf(',')).toString());
            } else {
                // If news is changed get the inital date only.
                String initial = StringUtils.substringAfter(date, "| ");
                createdDateTime = dtf.parseDateTime(initial.subSequence(0, initial.indexOf(',')).toString());
            }
        }
        logger.debug("Output date after transformation {}", createdDateTime);
        return createdDateTime;
    }

}
