package com.example.rss.web.resources;

import org.springframework.hateoas.ResourceSupport;

public class NewsResource extends ResourceSupport {

    private String title;

    private String uri;

    public NewsResource() {
        super();
    }

    public NewsResource(String title, String uri) {
        super();
        this.title = title;
        this.uri = uri;
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

}
