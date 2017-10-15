package com.example.rss.reading;

import com.example.rss.core.ProcessDnesBgHtmlPage;

public abstract class ReadingPage {

    public ProcessDnesBgHtmlPage getPage(int number) {
        String newsUrl = getUrl(number);
        ProcessDnesBgHtmlPage page = new ProcessDnesBgHtmlPage(newsUrl);
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
