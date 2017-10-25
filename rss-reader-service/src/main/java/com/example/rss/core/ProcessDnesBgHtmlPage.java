package com.example.rss.core;

import java.io.IOException;

import org.apache.commons.validator.routines.UrlValidator;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProcessDnesBgHtmlPage {
    private static final Logger logger = LoggerFactory.getLogger(ProcessDnesBgHtmlPage.class);

    private Document document;
    private String link;

    public ProcessDnesBgHtmlPage() {
        super();
    }

    public ProcessDnesBgHtmlPage(final String link) {
        logger.info("Reading news with url \n {}", link);
        this.link = link;
        this.document = removeUnnecessaryElements(getDocument(link));
    }

    public Document getDocument() {
        return document;
    }

    public void setDocument(Document document) {
        this.document = document;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
        this.document = removeUnnecessaryElements(getDocument(link));
    }

    /**
     * Return Document for the given link which has to be URL compatible, or
     * html String page which will be transformed to Document.
     *
     * @param link
     *            which has to be URL compatible
     * @return null if the URL is not valid or has error during reading
     */
    private Document getDocument(final String link) {
        if (!new UrlValidator().isValid(link)) {
            logger.warn("Link {} is not a valid URL", link);
            return null;
        }
        try {
            return Jsoup.connect(link).userAgent("Mozilla").get();
        } catch (IOException e) {
            logger.warn("Error during processing the page {}.", link);
            logger.debug("Error during processing the page {}.", e);
        }
        return null;
    }

    public Elements extractElementsByTag(String tagName) throws IOException {
        if (document.select(tagName) != null) {
            return document.select(tagName);
        } else {
            logger.warn("For the specified document, there is no elements with name {}", tagName);
            return null;
        }
    }

    public String extractInformationByTag(String tagName) throws IOException {
        if (document.select(tagName).first() != null) {
            return document.select(tagName).first().text();
        } else {
            logger.warn("For the specified document, there is no tag with name {}", tagName);
            return null;
        }
    }

    public String extractInformationByTagAndAttribute(String parentTag, String tagName, String attr)
            throws IOException {
        if (document.select(parentTag).first() != null) {
            String selected = document.select(parentTag).first().select(tagName).attr(attr);
            return selected.length() > 0 ? selected : null;
        } else {
            logger.warn("For the specified document, there is no tag with name {} and attribute {}.", tagName, attr);
            return null;
        }
    }
    
    public boolean isPresentInformationByTagAndAttribute(String parentTag, String tagName, String attr)
            throws IOException {
        if (document.select(parentTag).first() != null) {
            String selected = document.select(parentTag).first().select(tagName).attr(attr);
            return selected.length() > 0 ? true : false;
        } else {
            logger.warn("For the specified document, there is no presence of tag with name {} and attribute {}.",
                    tagName, attr);
            return false;
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
     * Remove other theme from the html. Div tag with class article_related
     * article_related_right is removed.
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

    protected Document removeFeedbackOnComments(Document document) {
        if (!document.select("div[class=feedback_comment]").isEmpty()) {
            document.select("div[class=feedback_comment]").remove();
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
        if (document != null) {
            removeOnThemeElements(document);
            removePictureElement(document);
            removeFeedbackOnComments(document);
        }
        return document;
    }

}