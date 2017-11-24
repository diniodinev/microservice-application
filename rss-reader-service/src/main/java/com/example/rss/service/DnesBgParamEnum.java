package com.example.rss.service;

/**
 * Contains all needed parameters keys needed for extraction of the news content
 * from Dbes.bg Their values must be read from the cloud-config.
 *
 */
@SuppressWarnings("all")
public enum DnesBgParamEnum {
    rootUrl, last, titleSelector, descriptionSelector, contentSelector, contentImage, uriImageSelector, authorSelector,
    authorAfterTextSelector, createdDateSelector, commentBoxSelector, commentTextSelector, commentUsernameSelector,
    commentUpSelector, commentDownSelector, slideShowSelector, commentUrl, commentUrlSeparator, slideShowAllImagesParentSelector
}
