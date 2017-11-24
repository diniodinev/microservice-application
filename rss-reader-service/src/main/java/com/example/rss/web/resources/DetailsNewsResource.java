package com.example.rss.web.resources;


import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class DetailsNewsResource extends NewsResource implements TemporalFormat {

    private ContentResource newsContant;

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
        this.newsContant = newsContant;
        this.authorResource = authorResource;   
        this.initialDate = initialDate;
    }

    public ContentResource getNewsContant() {
        return newsContant;
    }

    public Date getInitialDate() {
        return initialDate;
    }

    public void setNewsContant(ContentResource newsContant) {
        this.newsContant = newsContant;
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
