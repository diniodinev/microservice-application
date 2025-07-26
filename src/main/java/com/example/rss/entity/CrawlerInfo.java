package com.example.rss.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;

@Entity
@Table(name = "CrawlerInfo")
@Getter
@Setter
@NoArgsConstructor
public class CrawlerInfo extends AbstractAuditingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "CRAWLED_SITE_NAME", nullable = false, unique = true)
    private String crawledSiteName;

    @Column(name = "LAST_START", nullable = false)
    private OffsetDateTime initialDate;

    @Column(name = "LAST_CRAWLED_NEWS")
    private int lastCrawledNews;

    @Column(name = "FIRST_CRAWLED_NEWS")
    private int firstCrawledNews;

    @Column(name = "LAST_UPDATED_NEWS")
    private int lastUpdatedNews;

    @Column(name = "FIRST_UPDATE_NEWS")
    private int firstUpdatedNews;

    @Column(name = "LAST_READ")
    private int lastRead;
}
