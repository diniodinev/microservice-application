package com.example.rss.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.rss.core.BaseNewsHtmlPage;
import com.example.rss.entity.Author;
import com.example.rss.entity.Content;
import com.example.rss.entity.Image;
import com.example.rss.entity.News;
import com.example.rss.reading.ReadingDnesBgPage;
import com.example.rss.repository.AuthorRepository;
import com.example.rss.repository.NewsRepository;
import com.example.rss.utils.CustomDateUtils;
import com.example.rss.utils.DnesBgParams;

@Service
public class DnesbgExtractionNewsServiceImpl extends AbstractNewsExtraction {

    private static final Logger logger = LoggerFactory.getLogger(DnesbgExtractionNewsServiceImpl.class);

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private NewsRepository newsRepository;

    @Autowired
    private ReadingDnesBgPage readingDnesBgPage;

    @Autowired
    private DnesBgParams params;

    @Autowired
    private ImageService imageService;

    @Override
    @Transactional
    public News saveNews(Integer newsNumber) {
        News news = extractNews(newsNumber);
        if (news != null) {
            newsRepository.save(news);
        }
        return news;
    }

    @Override
    public News extractNews(Integer number) {
        String newsUrl = readingDnesBgPage.getUrl(number);
        BaseNewsHtmlPage page = readingDnesBgPage.getPage(number);

        if (page == null) {
            logger.warn("Page {} can not be processed. 404", number);
            return null;
        }

        List<Image> images = extractSlideShowImages(page);

        Content newsContent = new Content();
        newsContent.setNewsDescriptin(page.extractInformationByTag(params.getDescription()));
        newsContent.setNewsContent(page.extractInformationByTag(params.getContent()));
        newsContent.setImages(images);

        String authorNames = page.extractInformationAfter(params.getAuthor(), params.getAuthorAfterText());
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

    @Override
    public List<Image> extractSlideShowImages(BaseNewsHtmlPage page) {

        if (page == null) {
            logger.warn("Image extraction can't be done. Page is null.");
            return new LinkedList<>();
        }

        List<Image> allImages = new LinkedList<>();
        Image image;
        if (!page.isPresentInformationByTagAndAttribute(params.getContent(), params.getSlideShow(), "src")) {
            image = new Image();
            image.setLink(
                    page.extractInformationByTagAndAttribute(params.getUriImage(), params.getContentImage(), "src"));
            try {
                image.setByteData(imageService.extractData(image.getLink()));
            } catch (MalformedURLException e) {
                logger.warn("Image extraction can't be done. The url is not valid {}.", image.getLink());
            }
            allImages.add(image);
        } else {
            try {
                for (Element elementImage : page.extractElementsByTag(params.getSlideShowAllImagesParentSelector())) {
                    image = new Image();
                    image.setLink(normalizeDnesBgUrl(elementImage.select(params.getSlideShowAllImagesParentSelector())
                            .first().select("img").attr("src")));
                    image.setByteData(imageService.extractData(image.getLink()));
                    allImages.add(image);
                    logger.debug(String.format("Processed slideshow image: %s", image.getLink()));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return allImages;
    }

    private String normalizeDnesBgUrl(String url) {
        return StringUtils.replaceOnce(url, "//", "http://");
    }

    // Contains Specific DnesBgLogic
    private DateTime extractNewsCreatedDate(String date) {
        logger.debug("Input date before transformation {}", date);
        DateTime createdDateTime = new DateTime();
        Locale locale = new java.util.Locale("bg", "BG");
        // Replace 3 letters months with their full representation in order JODA
        // to parse them.
        date = CustomDateUtils.replaceWithFullMonthName(date);
        DateTimeFormatter dtf = DateTimeFormat.forPattern("dd MMM yyyy HH:mm").withLocale(locale);

        if (date != null) {
            if (!StringUtils.contains(date, '|')) {
                createdDateTime = dtf.parseDateTime(date.subSequence(0, date.indexOf(',')).toString());
            } else {
                // If news is changed get the initial date only.
                String initial = StringUtils.substringAfter(date, "| ");
                createdDateTime = dtf.parseDateTime(initial.subSequence(0, initial.indexOf(',')).toString());
            }
        }
        logger.debug("Output date after transformation {}", createdDateTime);
        return createdDateTime;
    }

}