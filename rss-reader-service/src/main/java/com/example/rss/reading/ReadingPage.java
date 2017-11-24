package com.example.rss.reading;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.rss.core.DnesBgHtmlPage;

public abstract class ReadingPage {

    private static final Logger logger = LoggerFactory.getLogger(ReadingPage.class);

    public DnesBgHtmlPage getPage(int number) {
        String newsUrl = getUrl(number);
        DnesBgHtmlPage page = new DnesBgHtmlPage(newsUrl);
        if (page.getDocument() == null) {
            return null;
        }
        return page;
    }

    public DnesBgHtmlPage getPage(String uri) {
        if (logger.isDebugEnabled()) {
            logger.debug("Processing uri {}", uri);
        }
        DnesBgHtmlPage page = new DnesBgHtmlPage(uri);
        if (page.getDocument() == null) {
            return null;
        }
        return page;
    }

    public abstract String getUrl();

    /**
     * Get Url based on the given <code>number</code>
     * 
     * @param number
     *            which take part of the URL forming
     * @return the URL representation
     */
    public abstract String getUrl(int number);

    public abstract String getUrl(String identifier);
}
