package com.example.rss.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

@Entity
@Table(name = "COMMENT")
public class Comment extends AbstractAuditingEntity {

    @Id
    @Column(name = "COMMENT_ID", nullable = true, unique = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Type(type = "text")
    private String authorName;

    @Type(type = "text")
    private String content;

    @Column(name = "LIKES")
    private int likes;

    @Column(name = "DISLIKES")
    private String dislikes;

    @ManyToOne
    @JoinColumn(name = "News_id", referencedColumnName = "NEWS_ID")
    News relatedNews;

    public Comment() {
        super();
    }

    public Comment(long id, String authorName, String content, int likes, String dislikes) {
        super();
        this.id = id;
        this.authorName = authorName;
        this.content = content;
        this.likes = likes;
        this.dislikes = dislikes;
    }

    public long getId() {
        return id;
    }

    public String getAuthorName() {
        return authorName;
    }

    public String getContent() {
        return content;
    }

    public int getLikes() {
        return likes;
    }

    public String getDislikes() {
        return dislikes;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public void setDislikes(String dislikes) {
        this.dislikes = dislikes;
    }

    public News getRelatedNews() {
        return relatedNews;
    }

    public void setRelatedNews(News relatedNews) {
        this.relatedNews = relatedNews;
    }

}
