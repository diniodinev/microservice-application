package com.example.rss.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

@Entity
@Table(name = "CrawlerInfo")
public class CrawlerInfo extends AbstractAuditingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "CRAWLED_SITE_NAME", nullable = false, updatable = true, unique = true)
    private String crawledSiteName;

    @Column(name = "LAST_START", insertable = true, updatable = true, nullable = false)
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime initialDate;

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

    public CrawlerInfo() {
        super();
    }

    public long getId() {
        return id;
    }

    public DateTime getInitialDate() {
        return initialDate;
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

    public void setId(long id) {
        this.id = id;
    }

    public void setInitialDate(DateTime initialDate) {
        this.initialDate = initialDate;
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

    public String getCrawledSiteName() {
        return crawledSiteName;
    }

    public void setCrawledSiteName(String crawledSiteName) {
        this.crawledSiteName = crawledSiteName;
    }
}
