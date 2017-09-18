package com.example.rss.exceptions;

public class FeedReaderException extends RuntimeException {
    
    private static final long serialVersionUID = 5979573714475874709L;

    public FeedReaderException(String message, Throwable cause) {
        super(message, cause);
    }
}