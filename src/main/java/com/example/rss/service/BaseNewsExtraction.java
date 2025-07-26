package com.example.rss.service;

import java.net.MalformedURLException;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.example.rss.core.BaseNewsHtmlPage;
import com.example.rss.entity.Author;
import com.example.rss.entity.Content;
import com.example.rss.entity.Image;
import com.example.rss.entity.News;
import com.example.rss.reading.ReadingPage;
import com.example.rss.repository.AuthorRepository;
import com.example.rss.repository.ContentRepository;
import com.example.rss.repository.NewsRepository;
import com.example.rss.utils.tags.ContentTags;
import com.example.rss.utils.tags.ImageTags;

public abstract class BaseNewsExtraction implements ExtractionNewsService {

	private static final Logger logger = LoggerFactory.getLogger(CapitalExtractionNewsServiceImpl.class);

	@Autowired
	private ImageService imageService;

	@Autowired
	private NewsRepository newsRepository;
	
	@Autowired
	private ContentRepository contentRepository;

	@Autowired
	private RssNotificationService rssNotificationService;

	public BaseNewsHtmlPage parsePage(Integer number, ReadingPage pageReader) {
		BaseNewsHtmlPage page = pageReader.getPage(number);
		return page;
	}

	@Override
	public Content extractContent(BaseNewsHtmlPage parsedPage, ContentTags contentTags, ImageTags imageTags) {
		Content newsContent = new Content();
		newsContent.setNewsDescription(parsedPage.extractInformationByTag(contentTags.getDescriptionTag()));
		newsContent.setNewsContent(parsedPage.extractInformationByTag(contentTags.getContentTag()));
		newsContent.setImages(extractSlideShowImages(parsedPage, imageTags));

		return newsContent;
	}

	@Override
	public Author extractAuthor(BaseNewsHtmlPage page, AuthorRepository authorRepository, String author) {

		Author newsAuthor = authorRepository.findOneByNames(author);
		if (newsAuthor == null) {
			newsAuthor = new Author();
			newsAuthor.setNames(author);
		}
		return newsAuthor;
	}

	@Override
	public News extractNews(News oldNews, BaseNewsHtmlPage page, Content newsContent, Author newsAuthor) {
		News newsToSave = null;
		if (oldNews == null) {
			newsToSave = new News();
			newsToSave.setNewsContent(newsContent);
		} else {
			newsToSave = oldNews;
			newsToSave.setId(oldNews.getId());
			
			Content newContent = contentRepository.findOne(oldNews.getId());
			newContent.setNewsContent(newsContent.getNewsContent());
			contentRepository.save(newContent);
		}

		
		newsToSave.setAuthor(newsAuthor);
		newsToSave.setUri(page.getLink());
		newsToSave.setRemainingNotificationTries(rssNotificationService.getTotalNotificationTries());
		return newsToSave;
	}

	@Override
	public List<Image> extractSlideShowImages(BaseNewsHtmlPage page, ImageTags imageTags) {
		if (page == null) {
			logger.warn("Image extraction can't be done. Page is null.");
			return new LinkedList<>();
		}

		List<Image> allImages = new LinkedList<>();

		Image image;
		if (!page.isImageInTag(imageTags.getSlideShowAllImagesParentSelector())) {
			image = new Image();
			image.setLink(constructImageUrl(page, imageTags));
			byte[] imageContent = new byte[0];
			try {
				imageContent = imageService.extractData(image.getLink());
				image.setByteData(imageService.extractData(image.getLink()));
			} catch (MalformedURLException e) {
				logger.warn("Image extraction can't be done. The url is not valid {}.", image.getLink());
			}
			if (imageContent != null) {
				allImages.add(image);
			}
		} else {
			try {
				for (String imageUrl : page.extractAllImageLinks(imageTags)) {
					image = new Image();
					image.setLink(imageUrl);
					image.setByteData(imageService.extractData(image.getLink()));
					allImages.add(image);
					logger.debug(String.format("Processed slideshow image: %s", image.getLink()));
				}
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
		}
		return allImages;
	}

	@Override
	@Transactional
	public News saveNews(Integer newsNumber) {
		News news = null;
		try {
			MDC.put("transactionId", UUID.randomUUID().toString());
			MDC.put("method", "saveNews");
			news = extractNews(newsNumber);
			if (news != null) {
				newsRepository.save(news);
			}
		} finally {
			MDC.clear();
		}
		return news;
	}

	@Override
	public String constructImageUrl(BaseNewsHtmlPage page, ImageTags imageTags) {
		return imageTags.getImagesBaseUrl()
				+ page.extractInformationByTagAndAttribute(imageTags.getSingleImageTag(), "img", "src");
	}

}
