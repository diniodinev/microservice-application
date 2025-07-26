package com.example.rss.service;

import static org.springframework.http.HttpStatus.NOT_FOUND;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import com.example.rss.core.BaseNewsHtmlPage;
import com.example.rss.entity.Author;
import com.example.rss.entity.Content;
import com.example.rss.entity.News;
import com.example.rss.reading.ReadingPage;
import com.example.rss.repository.AuthorRepository;
import com.example.rss.repository.NewsRepository;
import com.example.rss.utils.CustomDateUtils;
import com.example.rss.utils.DnesBgParams;
import com.example.rss.utils.tags.ContentTags;

@Service
public class DnesbgExtractionNewsServiceImpl extends BaseNewsExtraction {

    private static final Logger logger = LoggerFactory.getLogger(DnesbgExtractionNewsServiceImpl.class);

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private NewsRepository newsRepository;

    @Autowired
    private ContentTags dnesbgContentTags;

    @Autowired
    private DnesbgImageTags imageTags;

    @Autowired
    private ReadingPage readingDnesBgPage;

    @Autowired
    private DnesBgParams params;

    @Autowired
    private ImageService imageService;

    @Override
    public News extractNews(Integer number) {
        String newsUrl = readingDnesBgPage.getUrl(number);
        BaseNewsHtmlPage page = parsePage(number, readingDnesBgPage);

        if (page == null) {
            logger.warn("Page {} can not be processed. 404", number);
            throw new HttpClientErrorException(NOT_FOUND);
        }

        // Extract content and images
        Content newsContent = extractContent(page, dnesbgContentTags, imageTags);

        // Extract author
        Author newsAuthor = extractAuthor(page, authorRepository, null);

        // News information
        News oldNews = newsRepository.findOneByUri(newsUrl);

       	News newsToSave = extractNews(oldNews,page, newsContent, newsAuthor);
        newsToSave.setTitle(page.extractInformationByTag(params.getTitle()));
        newsToSave.setUri(newsUrl);
        newsToSave.setInitialDate(extractNewsCreatedDate(page.extractInformationByTag(params.getCreatedDate())).toInstant());

        newsContent.setNews(newsToSave);
        return newsToSave;
    }

    @Override
    public Author extractAuthor(BaseNewsHtmlPage page, AuthorRepository authorRepository, String author) {
        author = page.extractInformationAfter(params.getAuthor(), params.getAuthorAfterText());

        logger.debug("Extracted author names: {}", author);
        return super.extractAuthor(page, authorRepository, author);
    }

    // @Override
    // public List<Image> extractSlideShowImages(BaseNewsHtmlPage page) {
    //
    // if (page == null) {
    // logger.warn("Image extraction can't be done. Page is null.");
    // return new LinkedList<>();
    // }
    //
    // List<Image> allImages = new LinkedList<>();
    // Image image;
    // if
    // (!page.isPresentInformationByTagAndAttribute(dnesbgContentTags.getContentTag(),
    // params.getSlideShow(),
    // "src")) {
    // image = new Image();
    // image.setLink(
    // page.extractInformationByTagAndAttribute(params.getUriImage(),
    // params.getContentImage(), "src"));
    // try {
    // image.setByteData(imageService.extractData(image.getLink()));
    // } catch (MalformedURLException e) {
    // logger.warn("Image extraction can't be done. The url is not valid {}.",
    // image.getLink());
    // }
    // allImages.add(image);
    // } else {
    // try {
    // for (Element elementImage :
    // page.extractElementsByTag(params.getSlideShowAllImagesParentSelector()))
    // {
    // image = new Image();
    // image.setLink(normalizeDnesBgUrl(elementImage.select(params.getSlideShowAllImagesParentSelector())
    // .first().select("img").attr("src")));
    // image.setByteData(imageService.extractData(image.getLink()));
    // allImages.add(image);
    // logger.debug(String.format("Processed slideshow image: %s",
    // image.getLink()));
    // }
    // } catch (IOException e) {
    // e.printStackTrace();
    // }
    //
    // }
    // return allImages;
    // }

    private String normalizeDnesBgUrl(String url) {
        return StringUtils.replaceOnce(url, "//", "http://");
    }

    // Contains Specific DnesBgLogic
    private OffsetDateTime extractNewsCreatedDate(String date) {
        logger.debug("Input date before transformation {}", date);
        if (date == null) {
            return OffsetDateTime.now();
        }

        // Convert short month names to full (if needed)
        date = CustomDateUtils.replaceWithFullMonthName(date);

        Locale locale = new Locale("bg", "BG");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy HH:mm", locale);

        String datePart;
        if (!date.contains("|")) {
            datePart = date.substring(0, date.indexOf(','));
        } else {
            String initial = StringUtils.substringAfter(date, "| ");
            datePart = initial.substring(0, initial.indexOf(','));
        }

        LocalDateTime localDateTime = LocalDateTime.parse(datePart, formatter);
        OffsetDateTime createdDateTime = localDateTime.atOffset(ZoneOffset.UTC); // adjust offset as needed

        logger.debug("Output date after transformation {}", createdDateTime);
        return createdDateTime;
    }


}
