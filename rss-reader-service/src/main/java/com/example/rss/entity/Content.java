package com.example.rss.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

@Entity
@Table(name = "CONTENT")
public class Content extends AbstractAuditingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "CONTENT")
    @Type(type="text")  
    private String newsContent;
    
    @Column(name = "DESCRIPTION")
    private String newsDescriptin;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "content", cascade = CascadeType.ALL)
    private List<Images> images;
    
    @OneToOne(mappedBy="newsContant")
    private News news;

    public Content() {
        super();
    }

    public Content(long id, String content, List<Images> images) {
        super();
        this.id = id;
        this.newsContent = content;
        this.images = images;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNewsContent() {
        return newsContent;
    }

    public void setNewsContent(String newsContent) {
        this.newsContent = newsContent;
    }

    public List<Images> getImages() {
        return images;
    }

    public void setImages(List<Images> images) {
        this.images = images;
    }

    public String getNewsDescriptin() {
        return newsDescriptin;
    }

    public void setNewsDescriptin(String newsDescriptin) {
        this.newsDescriptin = newsDescriptin;
    }

    public News getNews() {
        return news;
    }

    public void setNews(News news) {
        this.news = news;
    }

}
