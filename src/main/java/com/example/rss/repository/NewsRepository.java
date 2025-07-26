package com.example.rss.repository;

import org.springframework.data.repository.CrudRepository;

import com.example.rss.entity.News;

public interface NewsRepository extends CrudRepository<News, Long> {
	
	News findOneByUri(String uri);
	
}
