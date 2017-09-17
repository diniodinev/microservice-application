package com.example.rss.service;

import java.io.IOException;
import java.net.MalformedURLException;

import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.FeedException;

public interface RssReaderService {
    
    public SyndFeed redFeed(String feedURL) throws MalformedURLException, IOException, IllegalArgumentException, FeedException;

}
