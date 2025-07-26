package com.example.rss.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "CONTENT")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Content extends AbstractAuditingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "CONTENT", columnDefinition = "TEXT")
    private String newsContent;

    @Column(name = "DESCRIPTION")
    private String newsDescription;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "Image_Id", referencedColumnName = "id")
    private List<Image> images;

    @OneToOne(mappedBy = "newsContent")
    private News news;
}
