package com.example.rss.service;

import com.example.rss.entity.Comment;

public interface CommentsService {

    Iterable<Comment> extractComments(int newsNumber);

}
