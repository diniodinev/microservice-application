package com.example.rss.repository;

import org.springframework.data.repository.CrudRepository;

import com.example.rss.entity.Image;

public interface ImagesRepository extends CrudRepository<Image, Long> {
}
