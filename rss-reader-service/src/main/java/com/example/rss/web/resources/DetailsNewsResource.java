package com.example.rss.web.resources;


import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.fasterxml.jackson.annotation.JsonProperty;

public class DetailsNewsResource extends NewsResource implements TemporalFormat {

    private ContentResource news;

    @JsonProperty("author")
    private AuthorResource authorResource;

    @JsonFormat(shape = Shape.STRING, pattern = TIMESTAMP)
    private Date initialDate;

    public DetailsNewsResource() {
        super();
    }

    public DetailsNewsResource(String title, String uri) {
        super(title, uri);
    }

    public DetailsNewsResource(ContentResource newsContant, AuthorResource authorResource, Date initialDate) {
        super();
        this.news = newsContant;
        this.authorResource = authorResource;   
        this.initialDate = initialDate;
    }

    public ContentResource getNews() {
        return news;
    }

    public Date getInitialDate() {
        return initialDate;
    }

    public void setNews(ContentResource news) {
        this.news = news;
    }

    public void setInitialDate(Date initialDate) {
        this.initialDate = initialDate;
    }

    public AuthorResource getAuthorResource() {
        return authorResource;
    }

    public void setAuthorResource(AuthorResource authorResource) {
        this.authorResource = authorResource;
    }

}
