package com.example.rss.service;

import java.net.MalformedURLException;
import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.rss.core.BaseNewsHtmlPage;
import com.example.rss.entity.Author;
import com.example.rss.entity.Content;
import com.example.rss.entity.Image;
import com.example.rss.entity.News;
import com.example.rss.reading.ReadingPage;
import com.example.rss.repository.AuthorRepository;
import com.example.rss.utils.tags.CapitalImageTags;
import com.example.rss.utils.tags.ContentTags;

public abstract class BaseNewsExtraction implements ExtractionNewsService {

    private static final Logger logger = LoggerFactory.getLogger(CapitalExtractionNewsServiceImpl.class);

    @Autowired
    private CapitalImageTags imageTags;

    @Autowired
    private ImageService imageService;

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

    @Override
    public News extractNews(BaseNewsHtmlPage page, Content newsContent, Author newsAuthor) {
        News newsToSave = new News();
        newsToSave.setNewsContant(newsContent);
        newsToSave.setAuthor(newsAuthor);
        newsToSave.setUri(page.getLink());
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
        if (!page.isImageInTag(imageTags.getSlideShowAllImagesParentSelector())) {
            image = new Image();
            image.setLink(constructImageUrl(page));
            try {
                image.setByteData(imageService.extractData(image.getLink()));
            } catch (MalformedURLException e) {
                logger.warn("Image extraction can't be done. The url is not valid {}.", image.getLink());
            }
            allImages.add(image);
        } else {
            try {
                for (String imageUrl : page.extractAllImageLinks(imageTags)) {
                    image = new Image();
                    image.setLink(imageUrl);
                    image.setByteData(imageService.extractData(image.getLink()));
                    allImages.add(image);
                    logger.debug(String.format("Processed slideshow image: %s", image.getLink()));
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
        return allImages;
    }

    @Override
    public String constructImageUrl(BaseNewsHtmlPage page) {
        return imageTags.getImagesBaseUrl()
                + page.extractInformationByTagAndAttribute(imageTags.getSingleImageTag(), "img", "src");
    }
}
