package com.example.rss.entity;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "NEWS")
@Access(value = AccessType.FIELD)
public class News extends AbstractAuditingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "NEWS_ID")
    private long id;

    @Column(name = "TITLE")
    private String title;

    @Column(name = "URI")   
    private String uri;

    @OneToOne(fetch = FetchType.EAGER,  cascade = CascadeType.ALL)
    @JoinColumn(name="CONTENT_NEWS_ID", referencedColumnName="ID")
    private Content newsContant;

    public News() {
        super();
    }

    public News(long id, String title, String uri, Content newsContant) {
        super();
        this.id = id;
        this.title = title;
        this.uri = uri;
        this.newsContant = newsContant;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public Content getNewsContant() {
        return newsContant;
    }

    public void setNewsContant(Content newsContant) {
        this.newsContant = newsContant;
    }

}
