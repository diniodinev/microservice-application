package com.example.rss.service;

import java.io.IOException;

import com.example.rss.entity.Comment;
import com.example.rss.entity.News;

public interface CommentsService {

    Iterable<Comment> extractComments(News news);

}
