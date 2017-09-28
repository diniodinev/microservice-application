package com.example.rss.resources;

import org.springframework.hateoas.ResourceSupport;

public class NewsResource extends ResourceSupport {

    private String title;

    private String uri;

    public NewsResource() {
        super();
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
