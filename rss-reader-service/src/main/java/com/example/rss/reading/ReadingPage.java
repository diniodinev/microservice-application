package com.example.rss.reading;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.rss.core.BaseNewsHtmlPage;

public abstract class ReadingPage {

    private static final Logger logger = LoggerFactory.getLogger(ReadingPage.class);

    private List<String> excludePaths;

    public BaseNewsHtmlPage getPage(int number) {
        String newsUrl = getUrl(number);
        BaseNewsHtmlPage page = new BaseNewsHtmlPage(newsUrl, excludePaths);
        if (page.getDocument() == null) {
            return null;
        }

        return page;
    }

    public BaseNewsHtmlPage getPage(String uri) {
        if (logger.isDebugEnabled()) {
            logger.debug("Processing uri {}", uri);
        }
        BaseNewsHtmlPage page = new BaseNewsHtmlPage(uri);
        if (page.getDocument() == null) {
            return null;
        }
        return page;
    }

    public List<String> getExcludePaths() {
        return excludePaths;
    }

    public void setExcludePaths(List<String> excludePaths) {
        this.excludePaths = excludePaths;
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
