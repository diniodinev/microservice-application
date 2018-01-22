package com.example.rss.web.resources;

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
