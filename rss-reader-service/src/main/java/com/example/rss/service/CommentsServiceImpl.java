package com.example.rss.service;

import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.rss.core.ProcessDnesBgHtmlPage;
import com.example.rss.entity.Comment;
import com.example.rss.entity.News;
import com.example.rss.reading.ReadingPage;
import com.example.rss.repository.CommentsRepository;
import com.example.rss.utils.DnesBgParams;

@Service
public class CommentsServiceImpl implements CommentsService {

    private static final Logger logger = LoggerFactory.getLogger(CommentsServiceImpl.class);

    @Autowired
    private CommentsRepository commentsRepository;

    @Autowired
    private ReadingPage readingPage;

    @Autowired
    private DnesBgParams params;

    public CommentsServiceImpl() {
        super();
    }

    @Override
    public Iterable<Comment> extractComments(News news) {
        ProcessDnesBgHtmlPage page = readingPage.getPage(news.getUri());

        List<Comment> allComments = new LinkedList<>();

        Elements comments;
        try {
            comments = page.extractElementsByTag(params.getCommentBox());

            int currentPage = 0;
            int allCommentsPerPage;
            do {
                allCommentsPerPage = 0;
                if (comments.isEmpty()) {
                    return null;
                } else {
                    List<Comment> pageComments = getSinglePageComments(news, ++currentPage);
                    allCommentsPerPage = pageComments.size();
                    if (allCommentsPerPage > 0) {
                        allComments.addAll(pageComments);
                    }
                }
            } while (allCommentsPerPage != 0);
        } catch (IOException e) {
            logger.warn("Commens can not be extracted.", e);
        }
        return commentsRepository.save(allComments);
    }

    private List<Comment> getSinglePageComments(News news, int currentNumber) {
        ProcessDnesBgHtmlPage page = readingPage.getPage(news.getUri() + "," + currentNumber);
        List<Comment> commentsList = new LinkedList<>();
        try {

            Elements comments = page.extractElementsByTag(params.getCommentBox());
            Iterator<Element> s = comments.iterator();

            while (s.hasNext()) {
                Element el = s.next();
                Comment nextComment = new Comment();
                nextComment.setRelatedNews(news);
                nextComment.setAuthorName(extractUsername(el.select(params.getCommentsUsername()).first().text()));
                nextComment.setContent(el.select(params.getCommentsText()).first().text());
                nextComment.setLikes(Integer.valueOf(el.select(params.getCommentsUp()).first().text()));
                nextComment.setDislikes(Integer.valueOf(el.select(params.getCommentsDown()).first().text()));
                commentsList.add(nextComment);
            }
        } catch (IOException e) {
            logger.warn("Commens can not be extracted.", e);
        }

        return commentsList;

    }

    private String extractUsername(String fullText) {
        if (fullText == null)
            return "";
        if (StringUtils.contains(fullText, "преди")) {
            return fullText.substring(0, StringUtils.indexOf(fullText, "преди"));
        }
        return fullText;

    }

}
