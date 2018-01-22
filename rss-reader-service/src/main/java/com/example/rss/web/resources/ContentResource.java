package com.example.rss.web.resources;

public class ContentResource extends NewsResource {
    private String newsContent;

    private String newsDescriptin;

    public ContentResource() {
        super();
    }

    public String getNewsContent() {
        return newsContent;
    }

    public String getNewsDescriptin() {
        return newsDescriptin;
    }

    public void setNewsContent(String newsContent) {
        this.newsContent = newsContent;
    }

    public void setNewsDescriptin(String newsDescriptin) {
        this.newsDescriptin = newsDescriptin;
    }

    public ContentResource(String newsContent, String newsDescriptin) {
        super();
        this.newsContent = newsContent;
        this.newsDescriptin = newsDescriptin;
    }

}
