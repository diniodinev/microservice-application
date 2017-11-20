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
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;
import org.springframework.stereotype.Component;

@Entity
@Table(name = "NEWS")
@Access(value = AccessType.FIELD)
@Component
public class News extends AbstractAuditingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "NEWS_ID")
    private long id;

    @Column(name = "TITLE")
    private String title;

    @Column(name = "URI")
    private String uri;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "CONTENT_NEWS_ID", referencedColumnName = "ID")
    private Content newsContant;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "AUTHOR_ID", referencedColumnName = "ID")
    private Author author;

    @Column(name = "INITAL_DATE", insertable = true, updatable = true, nullable = false)
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime initialDate;

    public News() {
        super();
    }

    public News(long id, String title, String uri, Content newsContant, Author author, DateTime initialDate) {
        super();
        this.id = id;
        this.title = title;
        this.uri = uri;
        this.newsContant = newsContant;
        this.author = author;
        this.initialDate = initialDate;
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

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public DateTime getInitialDate() {
        return initialDate;
    }

    public void setInitialDate(DateTime initialDate) {
        this.initialDate = initialDate;
    }

}
