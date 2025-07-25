package com.example.rss.core;

import com.example.rss.service.ImageTags;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.validator.routines.UrlValidator;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Base class which provides basic methods for page, parsing, tag extraction,
 * attribute extraction etc.
 *
 * @author Dinyo Dinev
 *
 */
public class BaseNewsHtmlPage {

    private static final Logger logger = LoggerFactory.getLogger(BaseNewsHtmlPage.class);

    private Document document;
    private String link;

    public BaseNewsHtmlPage() {
        super();
    }

    public BaseNewsHtmlPage(final String link) {
        logger.info("Reading news with url {}", link);
        this.link = link;
        this.document = removeUnnecessaryElements(getDocument(link), null);
    }

    public BaseNewsHtmlPage(final String link, final List<String> excludePaths) {
        logger.info("Reading news with url  {}", link);
        this.link = link;
        this.document = removeUnnecessaryElements(getDocument(link), excludePaths);
    }

    public Document getDocument() {
        return document;
    }

    public void setDocument(Document document) {
        this.document = document;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
        this.document = removeUnnecessaryElements(getDocument(link), null);
    }

    /**
     * Return Document for the given link which has to be URL compatible, or
     * html String page which will be transformed to Document.
     *
     * @param link
     *            which has to be URL compatible
     * @return null if the URL is not valid or has error during reading
     */
    public Document getDocument(final String link) {
        if (!new UrlValidator().isValid(link)) {
            logger.warn("Link {} is not a valid URL", link);
            return null;
        }
        try {
            return Jsoup.connect(link).userAgent("Mozilla").get();
        } catch (IOException e) {
            logger.warn("Error during processing the page {}.", link);
            logger.debug("Error during processing the page {}.", e);
        }
        return null;
    }

    public Elements extractElementsByTag(String tagName) {
        if (document.select(tagName) != null) {
            return document.select(tagName);
        } else {
            logger.warn("For the specified document, there is no elements with name {}", tagName);
            return null;
        }
    }

    public String extractInformationByTag(String tagName) {
        if (document.select(tagName).first() != null) {
            return document.select(tagName).first().text();
        } else {
            logger.warn("For the specified document, there is no tag with name {}", tagName);
            return null;
        }
    }

    public String extractInformationByTagAndAttribute(String parentTag, String tagName, String attr) {
        if (document.select(parentTag).first() != null) {
            String selected = document.select(parentTag).first().select(tagName).attr(attr);
            return selected.length() > 0 ? selected : null;
        } else {
            logger.warn("For the specified document, there is no tag with name {} and attribute {}.", tagName, attr);
            return null;
        }
    }

    public List<String> extractAllImageLinks(ImageTags imageTags) {
        List<String> allImagesRow = extractAllInformationInTag(imageTags.getSlideShowAllImagesParentSelector(),
                imageTags.getContentImage(), "src");
        List<String> resultUrls = new ArrayList<>(allImagesRow.size());
        if (!allImagesRow.isEmpty()) {
            for (String image : allImagesRow) {
                resultUrls.add(constructImageUrl(image, imageTags));
            }
        }
        return resultUrls;

    }

    /**
     * In a specific parent tag find all sub tags and extract their
     * <code>attr<code>.
     *
     * @param rootTag
     * @param cssQuerySelectorInRootElement
     * @param attr
     * @return
     */
    public List<String> extractAllInformationInTag(String rootTag, String cssQuerySelectorInRootElement, String attr) {
        Element rootElement = document.select(rootTag).first();
        List<String> extractedAttributes = new LinkedList<>();
        if (rootElement != null) {
            for (Element subElement : rootElement.select(cssQuerySelectorInRootElement)) {
                String selected = subElement.attr(attr);
                if (StringUtils.isNotBlank(selected)) {
                    extractedAttributes.add(selected.trim());
                }
            }
        } else {
            logger.warn("For the specified document, there is no tag with name {} and attribute {}.", rootTag, attr);
        }
        return extractedAttributes;
    }

    public String extractInformationByTagAndAttribute(String parentChildTag, String attr) {
        if (document.select(StringUtils.substringBefore(parentChildTag, " ")).first() != null) {
            String selected = document.select(StringUtils.substringBefore(parentChildTag, " ")).first()
                    .select(StringUtils.substringAfter(parentChildTag, " ")).attr(attr);
            return selected.length() > 0 ? selected : null;
        } else {
            logger.warn("For the specified document, there is no tag with name {} and attribute {}.",
                    StringUtils.substringAfter(parentChildTag, " "), attr);
            return null;
        }
    }

    public boolean isPresentInformationByTagAndAttribute(String parentTag, String tagName, String attr) {
        if (document.select(parentTag).first() != null) {
            String selected = document.select(parentTag).first().select(tagName).attr(attr);
            return selected.length() > 0 ? true : false;
        } else {
            logger.warn("For the specified document, there is no presence of tag with name {} and attribute {}.",
                    tagName, attr);
            return false;
        }
    }

    /**
     * Check if image with attribute src present in a given tag
     *
     * @param parentTag
     *            in which we search for an image
     * @return true if in tag with parent selector <code>parentTags</code>
     *         presents image with src HTML element.
     */
    public boolean isImageInTag(String parentTag) {
        if (document.select(parentTag).first() != null) {
            String selected = document.select(parentTag).first().select("img").attr("src");
            return selected.length() > 0 ? true : false;
        } else {
            logger.warn("For the specified document, there is no presence of tag with name {} and attribute {}.", "img",
                    "src");
            return false;
        }
    }

    public String getImageSource(Element elementImage, ImageTags imageTags) {
        if (elementImage != null) {
            String selected = elementImage.select("img").attr("src");
            return selected.length() > 0 ? constructImageUrl(selected, imageTags) : null;
        } else {
            logger.warn("For the specified document, there is no presence of tag with name {} and attribute {}.", "img",
                    "src");
            return null;
        }
    }

    /**
     * Construct absolute image path
     *
     * @param suffix
     *            extracted suffix of an image
     * @param imageTags
     *            <{@code ImageTags} object with configuration information for
     *            the images
     * @return absolute path for image or null if image is not found in a
     *         specific parentTag.
     */
    public String constructImageUrl(String suffix, ImageTags imageTags) {
        if (StringUtils.isNotBlank(suffix)) {
            return imageTags.getImagesBaseUrl() + suffix;
        }
        logger.warn("Absolute image path can't be constructed prefix is empty.");
        return null;
    }

    /**
     * Extracts the first trimmed content for the special <code>tagName</code>
     * and extract the part of it after special <code>separator</code>.
     *
     * @param tagName
     * @param separator
     * @return
     */
    public String extractInformationAfter(String tagName, String separator) {
    	if (document.select(tagName).first() != null) {
            return StringUtils.substringAfter(document.select(tagName).first().text(), separator).trim();
        } else {
            logger.warn("For the specified document, there is no tag with name {}", tagName);
            return null;
        }

    }

    /**
     * Remove all tags for the given selector <code>cssSelector</code> for given
     * {@link Document document}.
     *
     * @param document
     *            from which we want to remove tags
     * @param cssSelector
     *            which specifies one ore more tags which we want to remove
     * @return final version of the document which is without tags with
     *         specified cssSelector
     */
    public Document removeAllTags(final Document document, final String cssSelector) {
        if (document != null && StringUtils.isNotBlank(cssSelector) && !document.select(cssSelector).isEmpty()) {
            for (Element element : document.select(cssSelector)) {
                element.remove();
            }
        }
        return document;
    }

    /**
     * Remove all tags for the given selectors list <code>cssSelectors</code>
     * for given {@link Document document}.
     *
     * @param document
     *            from which we want to remove tags
     * @param cssSelectors
     *            which specifies one ore more tags which we want to remove
     * @return final version of the document which is without tags with
     *         specified cssSelector
     */
    public Document removeAllTags(final Document document, final List<String> cssSelectors) {
        for (String cssSelector : cssSelectors) {
            removeAllTags(document, cssSelector);
        }
        return document;
    }

    /**
     * Remove redundant code from the page.
     *
     * Note! Must be override in sub-classes.
     *
     * @param document
     *            in which we want to remove a content
     * @return sub-document cleaned from unnecessary elements.
     */
    public Document removeUnnecessaryElements(Document document, List<String> excludePaths) {
        if (excludePaths == null || excludePaths.isEmpty()) {
            logger.debug("No exclusion tags during page parsing.");
        } else {
            removeAllTags(document, excludePaths);
        }

        return document;
    }

}
