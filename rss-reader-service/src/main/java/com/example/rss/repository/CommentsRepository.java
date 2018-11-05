package com.example.rss.repository;

import org.springframework.data.repository.CrudRepository;

import com.example.rss.entity.Comment;

public interface CommentsRepository extends CrudRepository<Comment, Long> {
	
	Iterable<Comment> findAllByRelatedNewsId(long newsId);

}
