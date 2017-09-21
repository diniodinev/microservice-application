package com.example.rss.entity;

import javax.persistence.Entity;

import  javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.GenerationType;
import javax.persistence.GeneratedValue;
import javax.persistence.Column;

@Entity
@Table(name="NEWS_ENTITY")
public class NewsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "NEWS_ID")
    private long id;
    
    @Column(name = "TITLE")
    private String title;

    @Column(name = "URI")
    private String uri;

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
}
