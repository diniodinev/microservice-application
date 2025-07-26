package com.example.rss.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "COMMENT")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Comment extends AbstractAuditingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "COMMENT_ID")
    private long id;

    @Column(columnDefinition = "TEXT")
    private String authorName;

    @Column(columnDefinition = "TEXT")
    private String content;

    @Column(name = "LIKES")
    private int likes;

    @Column(name = "DISLIKES")
    private int dislikes;

    @ManyToOne
    @JoinColumn(name = "News_id", referencedColumnName = "NEWS_ID")
    private News relatedNews;
}
