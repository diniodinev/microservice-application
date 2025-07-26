package com.example.rss.service;

import java.net.MalformedURLException;

@FunctionalInterface
public interface ImageService {

    byte[] extractData(String urlPath) throws MalformedURLException;

}
