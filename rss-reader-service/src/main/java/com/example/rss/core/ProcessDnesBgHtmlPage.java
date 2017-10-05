package com.example.rss.core;

import org.apache.commons.validator.UrlValidator;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class ProcessDnesBgHtmlPage {
    private static final Logger logger = LoggerFactory.getLogger(ProcessDnesBgHtmlPage.class);

    private Document document;


    public ProcessDnesBgHtmlPage(final String link) throws IOException {
        logger.info("Reading news with url \n {}", link);
        this.document = removeUnnecessaryElements(getDocument(link));
    }

    /**
     * Return Document for the given link which has to be URL compatible, or
     * html String page which will be transformed to Document.
     *
     * @param link which has to be URL compatible
     * @return null if the URL is not valid
     * @throws IOException
     */
    private Document getDocument(final String link) throws IOException {
        if (!new UrlValidator().isValid(link)) {
            logger.warn("Link {} is not a valid URL", link);
            return null;
        }
        return Jsoup.connect(link).userAgent("Mozilla").get();
    }

    public String extractInformationByTag(String tagName) throws IOException {
        if (document.select(tagName).first() != null) {
            return document.select(tagName).first().text();
        } else {
            //TODO add meanigfyl logger
            logger.warn("For the specified document, there is no tag with name {}", tagName);
            return null;
        }
    }

    public String extractAuthor() {
        String author = null;
        if (!document.select("meta[itemprop=author]").isEmpty()) {
            Element meta = document.select("meta[itemprop=author]").first();
            author = meta.attr("content");

        }
        return author;
    }

    /**
     * Remove other theme from the html. Div tag with class
     * article_related article_related_right is removed.
     *
     * @param document
     * @return
     */
    protected Document removeOnThemeElements(Document document) {
        if (!document.select("div[class=article_related article_related_right]").isEmpty()) {
            document.select("div[class=article_related article_related_right]").first().remove();
        }
        return document;
    }

    protected Document removePictureElement(Document document) {
        if (!document.select("div[class=photo_descr]").isEmpty()) {
            document.select("div[class=photo_descr]").first().remove();
        }
        return document;
    }

    protected Document removeUnnecessaryElements(Document document) {
        removeOnThemeElements(document);
        removePictureElement(document);
        return document;
    }
}