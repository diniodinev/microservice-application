package com.example.rss.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "AUTHOR")
@Access(value = AccessType.FIELD)
public class Author extends AbstractAuditingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "AUTHOR_NAMES", unique = true)
    private String names;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "AUTHOR_ID", referencedColumnName = "ID")
    private List<News> news = new ArrayList<>();

    public Author() {
        super();
    }

    public long getId() {
        return id;
    }

    public String getNames() {
        return names;
    }

    public List<News> getNews() {
        return news;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setNames(String names) {
        this.names = names;
    }

    public void setNews(List<News> news) {
        this.news = news;
    }

}
