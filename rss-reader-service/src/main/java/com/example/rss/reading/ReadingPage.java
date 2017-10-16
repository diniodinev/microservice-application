package com.example.rss.reading;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.rss.core.ProcessDnesBgHtmlPage;

public abstract class ReadingPage {

    private static final Logger logger = LoggerFactory.getLogger(ReadingPage.class);

    public ProcessDnesBgHtmlPage getPage(int number) {
        String newsUrl = getUrl(number);
        ProcessDnesBgHtmlPage page = new ProcessDnesBgHtmlPage(newsUrl);
        if (page.getDocument() == null) {
            return null;
        }
        return page;
    }

    public ProcessDnesBgHtmlPage getPage(String uri) {
        if (logger.isDebugEnabled()) {
            logger.debug("Processing uri {}", uri);
        }
        ProcessDnesBgHtmlPage page = new ProcessDnesBgHtmlPage(uri);
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
}
