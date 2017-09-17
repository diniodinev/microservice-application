package com.example.rss.service;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import org.springframework.stereotype.Service;

import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;

@Service
public class RssReaderServiceImpl implements RssReaderService {

    @Override
    public SyndFeed redFeed(String feedURL) throws IOException, IllegalArgumentException, FeedException {
        URL url = new URL(feedURL);
        HttpURLConnection httpcon = (HttpURLConnection) url.openConnection();
        // Reading the feed
        SyndFeedInput input = new SyndFeedInput();
       return input.build(new XmlReader(httpcon));
    }
}
