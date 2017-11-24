package com.example.rss.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.rss.config.DnesbgProperties;
import com.example.rss.service.DnesBgParamEnum;

@Component
public class DnesBgParams {

    @Autowired
    private DnesbgProperties serviceProperties;

    public String getDescription() {
        return serviceProperties.getDnesbg().get(DnesBgParamEnum.descriptionSelector.name());
    }

    public String getContent() {
        return serviceProperties.getDnesbg().get(DnesBgParamEnum.contentSelector.name());
    }

    public String getContentImage() {
        return serviceProperties.getDnesbg().get(DnesBgParamEnum.contentImage.name());
    }

    public String getAuthor() {
        return serviceProperties.getDnesbg().get(DnesBgParamEnum.authorSelector.name());
    }

    /**
     * Specifies the text after which is the name of the author.
     * 
     * @return
     */
    public String getAuthorAfterText() {
        return serviceProperties.getDnesbg().get(DnesBgParamEnum.authorAfterTextSelector.name());
    }

    public String getTitle() {
        return serviceProperties.getDnesbg().get(DnesBgParamEnum.titleSelector.name());
    }

    public String getCreatedDate() {
        return serviceProperties.getDnesbg().get(DnesBgParamEnum.createdDateSelector.name());
    }

    public String getCommentBox() {
        return serviceProperties.getDnesbg().get(DnesBgParamEnum.commentBoxSelector.name());
    }

    public String getCommentsText() {
        return serviceProperties.getDnesbg().get(DnesBgParamEnum.commentTextSelector.name());
    }

    public String getCommentsUsername() {
        return serviceProperties.getDnesbg().get(DnesBgParamEnum.commentUsernameSelector.name());
    }

    public String getCommentsUp() {
        return serviceProperties.getDnesbg().get(DnesBgParamEnum.commentUpSelector.name());
    }

    public String getCommentsDown() {
        return serviceProperties.getDnesbg().get(DnesBgParamEnum.commentDownSelector.name());
    }

    public String getSlideShow() {
        return serviceProperties.getDnesbg().get(DnesBgParamEnum.slideShowSelector.name());
    }

    public String getCommentUrl() {
        return serviceProperties.getDnesbg().get(DnesBgParamEnum.commentUrl.name());
    }

    public String getCommentUrlSeparator() {
        return serviceProperties.getDnesbg().get(DnesBgParamEnum.commentUrlSeparator.name());
    }

}
