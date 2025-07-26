package com.example.rss.utils.tags;

public abstract class NewsTags {

    private String title;
    private String uri;
    private String initialDate;

    public String getTitle() {
        return title;
    }

    public String getUri() {
        return uri;
    }

    public String getInitialDate() {
        return initialDate;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public void setInitialDate(String initialDate) {
        this.initialDate = initialDate;
    }

}
