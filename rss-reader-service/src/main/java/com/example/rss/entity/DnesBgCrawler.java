package com.example.rss.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "DNESBG_CRAWLER")
public class DnesBgCrawler extends CrawlerInfo {

    @Column(name = "LAST_CRAWLED_NEWS", nullable = true, updatable = true)
    private int lastCrawledNews;

    @Column(name = "FIRST_CRAWLED_NEWS", nullable = true, updatable = true)
    private int firstCrawledNews;

    @Column(name = "LAST_UPDATED_NEWS", nullable = true, updatable = true)
    private int lastUpdatedNews;

    @Column(name = "FIRST_UPDATE_NEWS", nullable = true, updatable = true)
    private int firstUpdatedNews;

    @Column(name = "LAST_READ", nullable = true, updatable = true)
    private int lastRead;

    public DnesBgCrawler() {
        super();
    }

    public DnesBgCrawler(int lastCrawledNews, int firstCrawledNews, int lastUpdatedNews, int firstUpdatedNews,
            int lastRead) {
        super();
        this.lastCrawledNews = lastCrawledNews;
        this.firstCrawledNews = firstCrawledNews;
        this.lastUpdatedNews = lastUpdatedNews;
        this.firstUpdatedNews = firstUpdatedNews;
        this.lastRead = lastRead;
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

    @Override
    public String toString() {
        return "DnesBgCrawler [lastCrawledNews=" + lastCrawledNews + ", firstCrawledNews=" + firstCrawledNews
                + ", lastUpdatedNews=" + lastUpdatedNews + ", firstUpdatedNews=" + firstUpdatedNews + ", lastRead="
                + lastRead + "]";
    }

}
