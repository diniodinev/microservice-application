package com.example.rss.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Entity
@Table(name = "NEWS")
@Access(AccessType.FIELD)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Component
public class News extends AbstractAuditingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "NEWS_ID")
    private long id;

    @Column(name = "TITLE")
    private String title;

    @Column(name = "URI")
    private String uri;

    @Column(name = "REM_RETRIES")
    private long remainingNotificationTries;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "CONTENT_NEWS_ID", referencedColumnName = "ID")
    private Content newsContent;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "AUTHOR_ID", referencedColumnName = "ID")
    private Author author;

    @Column(name = "INITAL_DATE", nullable = false)
    private Instant initialDate;
}
