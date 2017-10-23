package com.example.rss.service;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.io.IOUtils;
import org.apache.commons.validator.routines.UrlValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ImageServiceImpl implements ImageService {
    private static final Logger logger = LoggerFactory.getLogger(ImageServiceImpl.class);

    @Override
    public byte[] extractData(String urlPath) throws MalformedURLException {
        if (urlPath == null) {
            logger.warn("Link {} is empty.", urlPath);
            return null;
        }
        if (!new UrlValidator().isValid(urlPath)) {
            logger.warn("Link {} is not a valid URL", urlPath);
            return null;
        }
        byte[] imageBytes = null;
        URL url = new URL(urlPath);
        try (InputStream is = url.openStream()) {
            imageBytes = IOUtils.toByteArray(is);
        } catch (IOException e) {
            logger.error("Failed reading image for page {}", urlPath);
        }
        return imageBytes;
    }
}
