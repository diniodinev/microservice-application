package com.example.rss.core;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class for processing Dnes.bg site.
 * 
 * @author Dinyo Dinev
 * @since 2017
 *
 */
public class DnesBgHtmlPage extends BaseNewsHtmlPage {
    private static final Logger logger = LoggerFactory.getLogger(DnesBgHtmlPage.class);

    public DnesBgHtmlPage() {
        super();
    }

    public DnesBgHtmlPage(final String link) {
        super(link);
        logger.info("Reading news with url \n {}", link);
        setLink(link);
        setDocument(removeUnnecessaryElements(getDocument(link)));
    }

    @Override
    public Document removeUnnecessaryElements(Document document) {
        if (logger.isDebugEnabled()) {
            logger.debug("Remove unnecessery elements from the page: %s .", getLink());
        }

        if (document != null) {
            removeAllTags(document, "div[class=article_related article_related_right]");
            removeAllTags(document, "div[class=feedback_comment]");
            removeAllTags(document, "div[class=photo_descr]");
            removeAdvertaising(document);
        }
        return document;
    }

    private Document removeAdvertaising(Document document) {
        if (!document.select("a[target=_blank]").isEmpty()) {
            for (Element element : document.select("a[target=_blank]")) {
                element.remove();
            }
        }
        return document;
    }

}