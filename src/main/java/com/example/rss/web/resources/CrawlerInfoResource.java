package com.example.rss.web.resources;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@JsonInclude(Include.NON_NULL)
public class CrawlerInfoResource extends NewsResource {

    private String crawledSiteName;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate lastStart;

    private int lastCrawledNews;
    private int firstCrawledNews;
    private int lastUpdatedNews;
    private int firstUpdatedNews;
    private int lastRead;

    public CrawlerInfoResource() {
        super();
    }

    public CrawlerInfoResource(String crawledSiteName, LocalDate lastStart, int lastCrawledNews, int firstCrawledNews,
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

    public void setCrawledSiteName(String crawledSiteName) {
        this.crawledSiteName = crawledSiteName;
    }

    public LocalDate getLastStart() {
        return lastStart;
    }

    public void setLastStart(LocalDate lastStart) {
        this.lastStart = lastStart;
    }

    public int getLastCrawledNews() {
        return lastCrawledNews;
    }

    public void setLastCrawledNews(int lastCrawledNews) {
        this.lastCrawledNews = lastCrawledNews;
    }

    public int getFirstCrawledNews() {
        return firstCrawledNews;
    }

    public void setFirstCrawledNews(int firstCrawledNews) {
        this.firstCrawledNews = firstCrawledNews;
    }

    public int getLastUpdatedNews() {
        return lastUpdatedNews;
    }

    public void setLastUpdatedNews(int lastUpdatedNews) {
        this.lastUpdatedNews = lastUpdatedNews;
    }

    public int getFirstUpdatedNews() {
        return firstUpdatedNews;
    }

    public void setFirstUpdatedNews(int firstUpdatedNews) {
        this.firstUpdatedNews = firstUpdatedNews;
    }

    public int getLastRead() {
        return lastRead;
    }

    public void setLastRead(int lastRead) {
        this.lastRead = lastRead;
    }
}
