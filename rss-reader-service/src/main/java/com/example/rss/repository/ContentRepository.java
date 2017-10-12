package com.example.rss.repository;

import org.springframework.data.repository.CrudRepository;

import com.example.rss.entity.Content;

public interface ContentRepository extends CrudRepository<Content, Long> {
}
