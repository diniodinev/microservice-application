package com.example.rss.service;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.rss.exceptions.FeedReaderException;
import com.example.rss.repository.NewsRepository;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;

@Service
public class RssReaderServiceImpl implements RssReaderService {
    private static final Log logger = LogFactory.getLog(RssReaderServiceImpl.class);
    
    @Autowired
    private NewsRepository newsRepository;

    /**
     * return SyndFeed or null if there is a problem with the feed reading
     */
    @Override
    public SyndFeed readFeed(String feedURL) {
        try {
            URL url = new URL(feedURL);
            HttpURLConnection httpcon = (HttpURLConnection) url.openConnection();
            // Reading the feed
            SyndFeedInput input = new SyndFeedInput();
            return input.build(new XmlReader(httpcon));
        } catch (MalformedURLException ex) {
            if (logger.isDebugEnabled()) {
                logger.debug("Cannot search for feeds [" + feedURL + "] because it cannot be converted to a valid URL: "
                        + ex.getMessage());
            }

        } catch (FeedException | IOException e) {
            throw new FeedReaderException("Failed to ingest feed", e);
        }
        return null;
    }
}
