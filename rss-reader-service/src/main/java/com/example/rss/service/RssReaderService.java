package com.example.rss.service;

import com.rometools.rome.feed.synd.SyndFeed;

public interface RssReaderService {
    
    public SyndFeed readFeed(String feedURL);

}
