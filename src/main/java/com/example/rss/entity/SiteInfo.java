package com.example.rss.entity;

import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "SITE_INFO")
@Access(value = AccessType.FIELD)
public class SiteInfo extends AbstractAuditingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "SITE_ID")
    private long id;

    @Column(name = "TITLE")
    private String title;

    @Column(name = "URI")
    private String uri;

    public SiteInfo() {
        super();
    }

    public SiteInfo(long id, String title, String uri) {
        super();
        this.id = id;
        this.title = title;
        this.uri = uri;
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getUri() {
        return uri;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

}
