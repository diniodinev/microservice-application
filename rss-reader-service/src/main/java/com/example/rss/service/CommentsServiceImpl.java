package com.example.rss.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.rss.entity.Comment;
import com.example.rss.entity.News;
import com.example.rss.repository.CommentsRepository;

@Service
public class CommentsServiceImpl implements CommentsService {

    @Autowired
    private CommentsRepository commentsRepository;

    public CommentsServiceImpl() {
        super();
    }

    @Override
    public void extractComments(News news) {
        Comment comment = new Comment();
        comment.setRelatedNews(news);
        comment.setAuthorName("Алов");
        commentsRepository.save(comment);

    }

}
