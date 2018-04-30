package com.example.rss.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;

import com.example.rss.core.BaseNewsHtmlPage;
import com.example.rss.entity.Author;
import com.example.rss.entity.Content;
import com.example.rss.entity.Image;
import com.example.rss.entity.News;
import com.example.rss.reading.ReadingPage;
import com.example.rss.repository.AuthorRepository;
import com.example.rss.repository.NewsRepository;
import com.example.rss.utils.tags.AuthorTags;
import com.example.rss.utils.tags.CapitalImageTags;
import com.example.rss.utils.tags.CapitalNewsTags;
import com.example.rss.utils.tags.ContentTags;
import com.example.rss.utils.tags.ImageTags;

@Service
@RefreshScope
public class CapitalExtractionNewsServiceImpl extends BaseNewsExtraction {

    private static final Logger logger = LoggerFactory.getLogger(CapitalExtractionNewsServiceImpl.class);

    @Autowired
    private ReadingPage readingCapitalPage;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private NewsRepository newsRepository;

    @Autowired
    private ContentTags capitalContentTags;

    @Autowired
    private AuthorTags authorTags;

    @Autowired
    private CapitalImageTags imageTags;

    @Autowired
    private CapitalNewsTags capitalNewsTags;

    @Autowired
    private ImageService imageService;

    @Override
    public News saveNews(Integer newsNumber) {
        News news = extractNews(newsNumber);
        if (news != null) {
            newsRepository.save(news);
        }
        return news;
    }

    @Override
    public News extractNews(Integer number) {
        BaseNewsHtmlPage page = parsePage(number, readingCapitalPage);

        if (page == null) {
            logger.warn("Page {} can not be processed. 404", number);
            return null;
        }

        // Extract content and images
        Content newsContent = extractContent(page, capitalContentTags);

        // Extract

        // Save
        Author newsAuthor = extractAuthor(page, authorRepository, null);

        // News information
        News newsToSave = extractNews(page, newsContent, newsAuthor);
        newsToSave.setInitialDate(extractNewsCreatedDate(page.extractInformationByTagAndAttribute(
                capitalNewsTags.getInitialDate(), capitalNewsTags.getInitialDateAttribute())));
        newsToSave.setTitle(page.extractInformationByTag(capitalNewsTags.getTitle()));

        newsContent.setNews(newsToSave);
        return newsToSave;
    }

    // Contains Specific DnesBgLogic
    private DateTime extractNewsCreatedDate(String date) {
        logger.debug("Input date before transformation {}", date);
        DateTime createdDateTime = new DateTime();
        Locale locale = new java.util.Locale("US");

        DateTimeFormatter dtf = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss").withLocale(locale);

        if (date != null) {
            createdDateTime = dtf.parseDateTime(date);
        } else {
            logger.debug("Created data not found. Now is set instead.");
            createdDateTime = DateTime.now();
        }
        logger.debug("Output date after transformation {}", createdDateTime);

        return createdDateTime;
    }

    @Override
    public Author extractAuthor(BaseNewsHtmlPage page, AuthorRepository authorRepository, String author) {
        author = page.extractInformationByTag(authorTags.getAuthorTag());
        if (logger.isDebugEnabled()) {
            logger.debug("Extracted author namesL {}", author);
        }
        return super.extractAuthor(page, authorRepository, author);
    }

    /*
     * @Override public List<Image> extractSlideShowImages(BaseNewsHtmlPage
     * page) { if (page == null) {
     * logger.warn("Image extraction can't be done. Page is null."); return new
     * LinkedList<>(); }
     * 
     * List<Image> allImages = new LinkedList<>();
     * 
     * Image image; if
     * (!page.isImageInTag(imageTags.getSlideShowAllImagesParentSelector())) {
     * image = new Image(); image.setLink(constructImageUrl(page)); try {
     * image.setByteData(imageService.extractData(image.getLink())); } catch
     * (MalformedURLException e) {
     * logger.warn("Image extraction can't be done. The url is not valid {}.",
     * image.getLink()); } allImages.add(image); } else { try { for (String
     * imageUrl : page.extractAllImageLinks(imageTags)) { image = new Image();
     * image.setLink(imageUrl);
     * image.setByteData(imageService.extractData(image.getLink()));
     * allImages.add(image);
     * logger.debug(String.format("Processed slideshow image: %s",
     * image.getLink())); } } catch (MalformedURLException e) {
     * e.printStackTrace(); } } return allImages; }
     */

}
