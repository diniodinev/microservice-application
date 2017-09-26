package com.example.rss.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Table;

import org.hibernate.annotations.ColumnDefault;

import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.GenerationType;
import javax.persistence.GeneratedValue;
import javax.persistence.Column;

@Entity
@Table(name = "NEWS_ENTITY")
public class NewsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "NEWS_ID")
    private long id;

    @Column(name = "TITLE")
    private String title;

    @Column(name = "URI")
    private String uri;

    @OneToOne(fetch = FetchType.EAGER)
    private Content newsContant;

    public NewsEntity() {
    }

    public NewsEntity(long id, String title, String uri, Content newsContant) {
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
