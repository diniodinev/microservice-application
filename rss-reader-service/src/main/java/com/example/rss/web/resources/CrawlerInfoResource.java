package com.example.rss.web.resources;

import static org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion.NON_NULL;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.joda.time.DateTime;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

@JsonSerialize(include=NON_NULL)
public class CrawlerInfoResource extends NewsResource {

    private String crawledSiteName;

    @DateTimeFormat(iso = ISO.DATE)
    private DateTime lastStart;

    private int lastCrawledNews;

    private int firstCrawledNews;

    private int lastUpdatedNews;

    private int firstUpdatedNews;

    private int lastRead;

    public CrawlerInfoResource() {
        super();
    }

    public CrawlerInfoResource(String crawledSiteName, DateTime lastStart, int lastCrawledNews, int firstCrawledNews,
            int lastUpdatedNews, int firstUpdatedNews, int lastRead) {
        super();
        this.crawledSiteName = crawledSiteName;
        this.lastStart = lastStart;
        this.lastCrawledNews = lastCrawledNews;
        this.firstCrawledNews = firstCrawledNews;
        this.lastUpdatedNews = lastUpdatedNews;
        this.firstUpdatedNews = firstUpdatedNews;
        this.lastRead = lastRead;
    }

    public String getCrawledSiteName() {
        return crawledSiteName;
    }

    public DateTime getLastStart() {
        return lastStart;
    }

    public int getLastCrawledNews() {
        return lastCrawledNews;
    }

    public int getFirstCrawledNews() {
        return firstCrawledNews;
    }

    public int getLastUpdatedNews() {
        return lastUpdatedNews;
    }

    public int getFirstUpdatedNews() {
        return firstUpdatedNews;
    }

    public int getLastRead() {
        return lastRead;
    }

    public void setCrawledSiteName(String crawledSiteName) {
        this.crawledSiteName = crawledSiteName;
    }

    public void setLastStart(DateTime lastStart) {
        this.lastStart = lastStart;
    }

    public void setLastCrawledNews(int lastCrawledNews) {
        this.lastCrawledNews = lastCrawledNews;
    }

    public void setFirstCrawledNews(int firstCrawledNews) {
        this.firstCrawledNews = firstCrawledNews;
    }

    public void setLastUpdatedNews(int lastUpdatedNews) {
        this.lastUpdatedNews = lastUpdatedNews;
    }

    public void setFirstUpdatedNews(int firstUpdatedNews) {
        this.firstUpdatedNews = firstUpdatedNews;
    }

    public void setLastRead(int lastRead) {
        this.lastRead = lastRead;
    }

}
