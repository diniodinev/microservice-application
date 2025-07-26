package com.example.rss.service;

import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.example.rss.core.BaseNewsHtmlPage;
import com.example.rss.entity.Comment;
import com.example.rss.reading.ReadingPage;
import com.example.rss.repository.CommentsRepository;
import com.example.rss.repository.NewsRepository;
import com.example.rss.utils.DnesBgParams;

@Service
public class CommentsServiceImpl implements CommentsService {

	private static final Logger logger = LoggerFactory.getLogger(CommentsServiceImpl.class);

	@Autowired
	private CommentsRepository commentsRepository;

	@Autowired
	private NewsRepository newsRepository;

	@Autowired
	@Qualifier("readingDnesBgPage")
	private ReadingPage readingPage;

	@Autowired
	private DnesBgParams params;

	public CommentsServiceImpl() {
		super();
	}

	@Override
	public Iterable<Comment> extractComments(int newsNumber, long newsId) {
		BaseNewsHtmlPage page = readingPage
				.getPage(params.getCommentUrl() + newsNumber + params.getCommentUrlSeparator() + 1);

		if (page == null) {
			logger.warn("Comments can not be processed. Error during extracting information for {}.",
					params.getCommentUrl() + newsNumber + params.getCommentUrlSeparator() + 1);
			return null;
		}
		List<Comment> allComments = new LinkedList<>();

		Elements comments;
		comments = page.extractElementsByTag(params.getCommentBox());

		int currentPage = 0;
		int allCommentsPerPage;
		do {
			allCommentsPerPage = 0;
			if (comments.isEmpty()) {
				return null;
			} else {
				List<Comment> pageComments = getSinglePageComments(newsNumber, ++currentPage, newsId);
				allCommentsPerPage = pageComments.size();
				if (allCommentsPerPage > 0) {
					allComments.addAll(pageComments);
				}
			}
		} while (allCommentsPerPage != 0);

		return updateComments(allComments, newsId);
	}

	private Iterable<Comment> updateComments(Iterable<Comment> comments, long newsId) {
		List<Comment> allStoredNewsComments = new LinkedList<>();
		commentsRepository.findAllByRelatedNewsId(newsId).forEach(allStoredNewsComments::add);

		for (Comment comment : comments) {
			// Check if the old and the new comments are the same.
			Optional<Comment> processedComment = allStoredNewsComments.parallelStream()
					.filter(c -> c.getContent().equals(comment.getContent())
							&& c.getAuthorName().equals(comment.getAuthorName()))
					.findFirst();
			if (!processedComment.isPresent()) {
				commentsRepository.save(comment);
			} else {
				// If present update its value
				processedComment.get().setLikes(comment.getLikes());
				processedComment.get().setDislikes(comment.getDislikes());
				//processedComment.get().setLastModifiedDate(LocalDateTime.now());

				// Update
				commentsRepository.save(processedComment.get());
			}
		}
		// TODO Retunred comments are not updated.For now we do not use this and it is
		// save.
		return comments;
	}

	private List<Comment> getSinglePageComments(int newsNumber, int currentNumber, long newsId) {
		BaseNewsHtmlPage page = readingPage
				.getPage(params.getCommentUrl() + newsNumber + params.getCommentUrlSeparator() + currentNumber);
		if (page == null) {
			return new LinkedList<>();
		}
		List<Comment> commentsList = new LinkedList<>();
		Elements comments = page.extractElementsByTag(params.getCommentBox());
		Iterator<Element> s = comments.iterator();

		while (s.hasNext()) {
			Element el = s.next();
			Comment nextComment = new Comment();
			nextComment.setAuthorName(extractUsername(el.select(params.getCommentsUsername()).first().text()));
			nextComment.setContent(el.select(params.getCommentsText()).get(1).text());
			nextComment.setLikes(Integer.valueOf(el.select(params.getCommentsUp()).first().text()));
			nextComment.setDislikes(Integer.valueOf(el.select(params.getCommentsDown()).first().text()));
			nextComment.setRelatedNews(newsRepository.findById(newsId).orElse(null));
			commentsList.add(nextComment);
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
