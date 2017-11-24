package com.example.rss.web.resources;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class AuthorResource extends NewsResource {
    private String names;

    public AuthorResource() {
        super();
    }

    public AuthorResource(String title, String uri) {
        super(title, uri);
    }

    public AuthorResource(String names) {
        super();
        this.names = names;
    }

    public String getNames() {
        return names;
    }

    public void setNames(String names) {
        this.names = names;
    }

}
